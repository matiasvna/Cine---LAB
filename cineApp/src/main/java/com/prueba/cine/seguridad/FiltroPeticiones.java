package com.prueba.cine.seguridad;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FiltroPeticiones implements Filter {

    private final Map<String, Long> registroPeticiones = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        // EXCEPCIÓN 1: Dejar pasar libremente los archivos de diseño
        String uri = req.getRequestURI();
        if (uri.startsWith("/css/") || uri.startsWith("/js/") || uri.startsWith("/img/")) {
            chain.doFilter(request, response);
            return; 
        }

        // EXCEPCIÓN 2: Limite de tiempo a los eenvios de formularios
        // Permite que las redirecciones automaticas (GET) funcionen
        if (req.getMethod().equalsIgnoreCase("POST")) {
            String ipCliente = req.getRemoteAddr();
            long tiempoActual = System.currentTimeMillis();

            // Límite: si intentó enviar otro formulario en menos de 500 milisegundos
            if (registroPeticiones.containsKey(ipCliente)) {
                long ultimaPeticion = registroPeticiones.get(ipCliente);
                
                if (tiempoActual - ultimaPeticion < 500) {
                    res.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                    res.setContentType("text/plain;charset=UTF-8");
                    res.getWriter().write("Error 429: Demasiadas peticiones. Por favor, espera un momento.");
                    return; 
                }
            }
            registroPeticiones.put(ipCliente, tiempoActual);
        }

        // Si todo está bien, dejamos que la peticion siga
        chain.doFilter(request, response);
    }
}