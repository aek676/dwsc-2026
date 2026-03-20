package com.university.news.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Noticia {
    private static final int MIN_DESC_CORTA = 5;
    private static final int MAX_DESC_CORTA = 30;
    private static final int MIN_DESC_LARGA = 20;
    private static final int MAX_DESC_LARGA = 250;
    private static final int MIN_ETIQUETAS = 1;
    private static final int MAX_ETIQUETAS = 6;
    private static final Pattern FECHA_PATTERN = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");
    private static final Pattern ETIQUETA_PATTERN = Pattern.compile("^#[\\wáéíóúñüÁÉÍÓÚÑÜ]+$");
    private static final List<String> NIVELES_VALIDOS = Arrays.asList("alta", "media", "baja");

    private String fecha;
    private String nivel;
    private String descripcionCorta;
    private String descripcionLarga;
    private List<String> etiquetas;

    public Noticia() {
        this.etiquetas = new ArrayList<>();
    }

    public Noticia(String fecha, String nivel, String descripcionCorta, 
                   String descripcionLarga, List<String> etiquetas) {
        this.fecha = fecha;
        this.nivel = nivel;
        this.descripcionCorta = descripcionCorta;
        this.descripcionLarga = descripcionLarga;
        this.etiquetas = etiquetas != null ? etiquetas : new ArrayList<>();
    }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public String getDescripcionCorta() { return descripcionCorta; }
    public void setDescripcionCorta(String descripcionCorta) { this.descripcionCorta = descripcionCorta; }

    public String getDescripcionLarga() { return descripcionLarga; }
    public void setDescripcionLarga(String descripcionLarga) { this.descripcionLarga = descripcionLarga; }

    public List<String> getEtiquetas() { return etiquetas; }
    public void setEtiquetas(List<String> etiquetas) { this.etiquetas = etiquetas; }

    public boolean validarFecha() {
        if (fecha == null || !FECHA_PATTERN.matcher(fecha).matches()) return false;
        String[] partes = fecha.split("/");
        int dia = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int anio = Integer.parseInt(partes[2]);
        if (mes < 1 || mes > 12) return false;
        if (dia < 1 || dia > 31) return false;
        return true;
    }

    public boolean validarNivel() {
        return nivel != null && NIVELES_VALIDOS.contains(nivel.toLowerCase());
    }

    public boolean validarDescripcionCorta() {
        if (descripcionCorta == null) return false;
        int len = descripcionCorta.replaceAll("\\s", "").length();
        return len >= MIN_DESC_CORTA && len <= MAX_DESC_CORTA;
    }

    public boolean validarDescripcionLarga() {
        if (descripcionLarga == null) return false;
        int len = descripcionLarga.replaceAll("\\s", "").length();
        return len >= MIN_DESC_LARGA && len <= MAX_DESC_LARGA;
    }

    public boolean validarEtiquetas() {
        if (etiquetas == null || etiquetas.isEmpty()) return false;
        if (etiquetas.size() < MIN_ETIQUETAS || etiquetas.size() > MAX_ETIQUETAS) return false;
        for (String tag : etiquetas) {
            if (!ETIQUETA_PATTERN.matcher(tag).matches()) return false;
        }
        return true;
    }

    public List<String> validar() {
        List<String> errores = new ArrayList<>();
        if (!validarFecha()) errores.add("Fecha debe tener formato dd/mm/aaaa");
        if (!validarNivel()) errores.add("Nivel debe ser 'alta', 'media' o 'baja'");
        if (!validarDescripcionCorta()) errores.add("Descripcion corta debe tener 5-30 caracteres (sin espacios)");
        if (!validarDescripcionLarga()) errores.add("Descripcion larga debe tener 20-250 caracteres (sin espacios)");
        if (!validarEtiquetas()) errores.add("Etiquetas: 1-6 hashtags (#palabra)");
        return errores;
    }

    public boolean esValida() {
        return validar().isEmpty();
    }

    @Override
    public String toString() {
        return "Noticia{fecha='" + fecha + "', nivel='" + nivel + 
               "', descripcionCorta='" + descripcionCorta + 
               "', descripcionLarga='" + descripcionLarga + 
               "', etiquetas=" + etiquetas + "}";
    }
}
