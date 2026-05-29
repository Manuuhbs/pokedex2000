package apoio;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Gera PDFs tabulares a partir de listas de objetos.
 *
 * Melhorias em relação à versão original:
 *  - Larguras de coluna calculadas dinamicamente a partir dos dados reais
 *  - Suporte à anotação @ColunaPDF para renomear/ocultar campos
 *  - Quebra de página extraída para método dedicado (sem duplicação)
 *  - wrap() lida corretamente com palavras maiores que a largura da coluna
 *  - Tipos numéricos alinhados à direita automaticamente
 *  - Parâmetros de layout agrupados em PageConfig (imutável)
 *  - Javadoc completo em todos os métodos públicos e privados relevantes
 *
 * @author mateus (refatorado)
 */
public class PDFManager {

    // ------------------------------------------------------------------ //
    //  Anotação opcional para personalizar colunas                        //
    // ------------------------------------------------------------------ //

    /**
     * Anote campos da sua entidade com @ColunaPDF para:
     *  - renomear o cabeçalho  (label)
     *  - forçar uma largura fixa (largura > 0)
     *  - ocultar o campo do relatório (ocultar = true)
     */
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    @java.lang.annotation.Target(java.lang.annotation.ElementType.FIELD)
    public @interface ColunaPDF {
        String  label   () default "";
        int     largura () default 0;
        boolean ocultar () default false;
    }

    // ------------------------------------------------------------------ //
    //  Configuração de página (record-like, imutável)                     //
    // ------------------------------------------------------------------ //

    /** Agrupa todos os parâmetros de layout em um único objeto. */
    private static final class PageConfig {
        final PDRectangle pageSize   = PDRectangle.A4;
        final float       margin     = 50f;
        final float       leading    = 14.5f;
        final int         fontSize   = 10;
        final float       pageWidth  = pageSize.getWidth();
        final float       pageHeight = pageSize.getHeight();
        final int         maxLines   = (int) ((pageHeight - 2 * margin) / leading);
    }

    // ------------------------------------------------------------------ //
    //  API pública                                                         //
    // ------------------------------------------------------------------ //

    /**
     * Gera um PDF retrato com tabela de dados a partir de uma lista de objetos.
     *
     * <p>Campos anotados com {@code @ColunaPDF(ocultar = true)} são ignorados.
     * Larguras de coluna são calculadas automaticamente pelo maior valor de cada
     * campo, respeitando o espaço disponível na página.</p>
     *
     * @param objetos        Lista não vazia de objetos da mesma classe
     * @param caminhoArquivo Caminho completo do arquivo PDF a ser salvo
     * @throws IOException              Erro de E/S ao escrever o arquivo
     * @throws IllegalArgumentException Lista nula ou vazia
     */
    public static void gerar(List<?> objetos, String caminhoArquivo) throws IOException {
        if (objetos == null || objetos.isEmpty())
            throw new IllegalArgumentException("Lista de objetos está vazia.");

        PageConfig cfg = new PageConfig();

        try (PDDocument doc = new PDDocument()) {
            PDType1Font font   = new PDType1Font(FontName.COURIER);
            Field[]     fields = camposVisiveis(objetos.get(0).getClass());

            // Calcula larguras dinâmicas e cabeçalhos
            int[]    colWidths = calcularLarguras(objetos, fields, cfg);
            String[] headers   = cabecalhos(fields);
            String   header    = formatRow(headers, colWidths);
            String   divisor   = "-".repeat(header.length());

            // Estado de paginação
            RenderState rs = iniciarPagina(doc, cfg, font, header, divisor);

            for (Object obj : objetos) {
                rs = renderizarObjeto(obj, fields, colWidths, doc, cfg, font,
                                      header, divisor, rs);
            }

            rs.out.endText();
            rs.out.close();
            doc.save(new File(caminhoArquivo));

        } catch (IllegalAccessException e) {
            throw new RuntimeException("Erro ao acessar atributos dos objetos: "
                                       + e.getMessage(), e);
        }
    }

    // ------------------------------------------------------------------ //
    //  Reflexão / metadados                                               //
    // ------------------------------------------------------------------ //

    /** Retorna os campos não ocultos da classe, com acesso liberado. */
    private static Field[] camposVisiveis(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                     .filter(f -> {
                         ColunaPDF ann = f.getAnnotation(ColunaPDF.class);
                         return ann == null || !ann.ocultar();
                     })
                     .peek(f -> f.setAccessible(true))
                     .toArray(Field[]::new);
    }

    /** Retorna o rótulo de cada campo (anotação ou nome do campo). */
    private static String[] cabecalhos(Field[] fields) {
        return Arrays.stream(fields)
                     .map(f -> {
                         ColunaPDF ann = f.getAnnotation(ColunaPDF.class);
                         return (ann != null && !ann.label().isEmpty())
                                ? ann.label() : f.getName();
                     })
                     .toArray(String[]::new);
    }

    // ------------------------------------------------------------------ //
    //  Cálculo de larguras dinâmicas                                      //
    // ------------------------------------------------------------------ //

    /**
     * Calcula a largura de cada coluna em caracteres.
     *
     * <p>Regras (em ordem de prioridade):
     * <ol>
     *   <li>Se o campo tiver {@code @ColunaPDF(largura > 0)}, usa esse valor.</li>
     *   <li>Caso contrário, usa o maior comprimento entre cabeçalho e valores.</li>
     *   <li>Distribui proporcionalmente se a soma ultrapassar a linha disponível.</li>
     * </ol>
     */
    private static int[] calcularLarguras(List<?> objetos, Field[] fields,
                                          PageConfig cfg) throws IllegalAccessException {
        String[] headers = cabecalhos(fields);
        int[]    widths  = new int[fields.length];

        // Inicializa com o tamanho do cabeçalho
        for (int i = 0; i < fields.length; i++)
            widths[i] = headers[i].length();

        // Expande conforme os dados reais
        for (Object obj : objetos) {
            for (int i = 0; i < fields.length; i++) {
                ColunaPDF ann = fields[i].getAnnotation(ColunaPDF.class);
                if (ann != null && ann.largura() > 0) {
                    widths[i] = ann.largura();
                    continue;
                }
                Object v = fields[i].get(obj);
                String s = v == null ? "" : v.toString();
                // Considera apenas a primeira "palavra" para palavras longas
                int maxPalavra = Arrays.stream(s.split("\\s+"))
                                       .mapToInt(String::length)
                                       .max().orElse(0);
                widths[i] = Math.max(widths[i], Math.min(s.length(), maxPalavra));
            }
        }

        // Espaço disponível em caracteres (Courier 10pt ≈ 6 px → ~85 colunas em A4)
        // Limitamos conservadoramente para não ultrapassar a margem.
        int disponivel = (int) ((cfg.pageWidth - 2 * cfg.margin) / 6) - fields.length * 3;
        int total = Arrays.stream(widths).sum();
        if (total > disponivel) {
            // Reduz proporcionalmente
            for (int i = 0; i < widths.length; i++)
                widths[i] = Math.max(4, (int) Math.floor((double) widths[i] / total * disponivel));
        }
        return widths;
    }

    // ------------------------------------------------------------------ //
    //  Renderização                                                        //
    // ------------------------------------------------------------------ //

    /**
     * Estado mutável de paginação passado entre chamadas de renderização.
     * Evita variáveis soltas espalhadas pelo método principal.
     */
    private static final class RenderState {
        PDPageContentStream out;
        int                 line;

        RenderState(PDPageContentStream out, int line) {
            this.out  = out;
            this.line = line;
        }
    }

    /** Inicia a primeira página e escreve cabeçalho + divisor. */
    private static RenderState iniciarPagina(PDDocument doc, PageConfig cfg,
                                             PDFont font, String header,
                                             String divisor) throws IOException {
        PDPage page = new PDPage(cfg.pageSize);
        doc.addPage(page);
        PDPageContentStream out = abrirStream(doc, page, font, cfg);
        writeLine(out, header);
        writeLine(out, divisor);
        return new RenderState(out, 2);
    }

    /**
     * Verifica se é necessário quebrar página e, em caso positivo,
     * fecha o stream atual, abre um novo e reescreve cabeçalho/divisor.
     */
    private static RenderState verificarQuebra(RenderState rs, PDDocument doc,
                                               PageConfig cfg, PDFont font,
                                               String header,
                                               String divisor) throws IOException {
        if (rs.line >= cfg.maxLines - 2) {
            rs.out.endText();
            rs.out.close();
            PDPage page = new PDPage(cfg.pageSize);
            doc.addPage(page);
            PDPageContentStream out = abrirStream(doc, page, font, cfg);
            writeLine(out, header);
            writeLine(out, divisor);
            return new RenderState(out, 2);
        }
        return rs;
    }

    /**
     * Renderiza um único objeto como bloco de linhas na tabela,
     * tratando word-wrap por coluna e quebra de página.
     */
    private static RenderState renderizarObjeto(Object obj, Field[] fields,
                                                int[] colWidths, PDDocument doc,
                                                PageConfig cfg, PDFont font,
                                                String header, String divisor,
                                                RenderState rs)
            throws IOException, IllegalAccessException {

        // Valores e quebra de palavras por coluna
        List<String[]> colPartes = new ArrayList<>();
        List<Boolean>  ehNumero  = new ArrayList<>();

        for (int i = 0; i < fields.length; i++) {
            Object v = fields[i].get(obj);
            String s = v == null ? "" : v.toString();
            colPartes.add(wrap(s, colWidths[i]));
            ehNumero.add(v instanceof Number);
        }

        int linhasNecessarias = colPartes.stream()
                                         .mapToInt(a -> a.length)
                                         .max().orElse(1);

        for (int l = 0; l < linhasNecessarias; l++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < fields.length; c++) {
                String txt = l < colPartes.get(c).length ? colPartes.get(c)[l] : "";
                sb.append(ehNumero.get(c)
                          ? fixRight(txt, colWidths[c])
                          : fixLeft(txt, colWidths[c]));
                sb.append(" | ");
            }
            writeLine(rs.out, sb.toString());
            rs.line++;
            rs = verificarQuebra(rs, doc, cfg, font, header, divisor);
        }

        // Linha divisória após o registro
        writeLine(rs.out, divisor);
        rs.line++;
        rs = verificarQuebra(rs, doc, cfg, font, header, divisor);

        return rs;
    }

    // ------------------------------------------------------------------ //
    //  Utilitários de texto                                               //
    // ------------------------------------------------------------------ //

    /**
     * Formata uma linha de acordo com os valores e larguras das colunas.
     * Alinha à esquerda (usado para cabeçalhos).
     */
    private static String formatRow(String[] vals, int[] colWidths) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vals.length; i++)
            sb.append(fixLeft(vals[i], colWidths[i])).append(" | ");
        return sb.toString();
    }

    /** Padding à esquerda (alinhamento esquerdo). Trunca se necessário. */
    private static String fixLeft(String txt, int w) {
        if (txt.length() > w) return txt.substring(0, w);
        return String.format("%-" + w + "s", txt);
    }

    /** Padding à direita (alinhamento direito, para números). Trunca se necessário. */
    private static String fixRight(String txt, int w) {
        if (txt.length() > w) return txt.substring(0, w);
        return String.format("%" + w + "s", txt);
    }

    /**
     * Quebra {@code txt} em linhas de no máximo {@code w} caracteres,
     * respeitando espaços quando possível.
     *
     * <p>Palavras maiores que {@code w} são fatiadas forçosamente.</p>
     */
    private static String[] wrap(String txt, int w) {
        if (txt == null || txt.isEmpty()) return new String[]{""};

        List<String>  linhas = new ArrayList<>();
        StringBuilder atual  = new StringBuilder();

        for (String palavra : txt.split("\\s+")) {
            // Palavra maior que a coluna: fatia
            while (palavra.length() > w) {
                if (atual.length() > 0) { linhas.add(atual.toString()); atual.setLength(0); }
                linhas.add(palavra.substring(0, w));
                palavra = palavra.substring(w);
            }
            if (palavra.isEmpty()) continue;

            if (atual.length() == 0) {
                atual.append(palavra);
            } else if (atual.length() + 1 + palavra.length() <= w) {
                atual.append(' ').append(palavra);
            } else {
                linhas.add(atual.toString());
                atual.setLength(0);
                atual.append(palavra);
            }
        }
        if (!atual.isEmpty()) linhas.add(atual.toString());
        return linhas.isEmpty() ? new String[]{""} : linhas.toArray(String[]::new);
    }

    // ------------------------------------------------------------------ //
    //  PDFBox helpers                                                     //
    // ------------------------------------------------------------------ //

    /** Abre um novo {@link PDPageContentStream} posicionado na margem superior. */
    private static PDPageContentStream abrirStream(PDDocument doc, PDPage page,
                                                   PDFont font,
                                                   PageConfig cfg) throws IOException {
        PDPageContentStream s = new PDPageContentStream(doc, page);
        s.setFont(font, cfg.fontSize);
        s.beginText();
        s.setLeading(cfg.leading);
        s.newLineAtOffset(cfg.margin, cfg.pageHeight - cfg.margin);
        return s;
    }

    /** Escreve uma linha de texto e avança para a próxima. */
    private static void writeLine(PDPageContentStream s, String txt) throws IOException {
        s.showText(txt);
        s.newLine();
    }
}