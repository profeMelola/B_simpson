package es.daw.simpson.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import es.daw.simpson.model.Personaje;
import es.daw.simpson.repository.PersonajeRepository;
import es.daw.simpson.service.PersonajeService;
import es.daw.simpson.util.Utils;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/personajes")
public class PersonajeServlet extends HttpServlet {

    // NO VAMOS A USAR REPOSITORIOS DIRECTAMENTE DEL SERVLET, VAMOS A USAR SERVICIOS!!!!
    private final PersonajeService personajeService = new PersonajeService();


//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//
//        // lo que me de la gana...
//    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // --------------------------------------------------
        // 1. LEER LOS PARÁMETROS DEL REQUEST
        String lugar = request.getParameter("lugar");
        System.out.println("**** lugar: " + lugar);

        // pendiente!!!! si hay error al convertir que mande un mensaje de error a personajes.jsp
        //Integer edadMax = Integer.valueOf(request.getParameter("edadMax"));
        //int edadMax = Integer.parseInt(request.getParameter("edadMax"));
        String edadMax = request.getParameter("edadMax"); // cuidadín!!! llega como un String pero la edad la trato como un int
        System.out.println("**** edadMax: " + edadMax);

        //boolean descendente = Boolean.parseBoolean(request.getParameter("descendente"));
        boolean descendente = request.getParameter("descendente") != null; // si no está marcado no se envía!!!
        System.out.println("**** descendente: " + descendente);

        String ordenarPor = request.getParameter("ordenarPor");

        String limite = request.getParameter("limite"); // Inter

        // --------------------------------------------------
        // 2. CONVERTIR Y VALIDAR LOS DATOS DE LOS PARÁMETROS
        List<Personaje> personajes = new ArrayList<>();

        try {
            Integer edadMaxInt = Utils.leerEntero("edadMax", edadMax);
            Integer limiteInt = Utils.leerEntero("limite", limite);

            // --------------------------------------------------
            // 3. LÓGICA DE NEGOCIO QUE HARÁ UN SERVICIO. OBTENER LA LISTA DE LOS PERSONAJES (con o sin filtro, con o sin ordenación...)
            personajes = personajeService.buscar(lugar, edadMaxInt, ordenarPor, descendente, limiteInt);

        }catch (Exception e){

            // si no es la primera carga, que pinte el mensaje
            //enviar mensaje error a la personajes.jsp
            if (request.getParameter("primer") == null)
                request.setAttribute("error", e.getMessage());

            // si es la primera carga, desde el index, que haga la búsqueda por defecto
            // búsqueda por defecto...
            if (request.getParameter("primer") != null)
                personajes = personajeService.buscar("", Integer.MAX_VALUE, "", true, Integer.MAX_VALUE);
        }

        // --------------------------------------------------
        // 4. ENVIAR A LA VISTA LA INFORMACIÓN PERTINENTE (MODELO)
        request.setAttribute("personajes", personajes);
        request.setAttribute("lugares", personajeService.lugaresDisponibles());

        // 5. REENVIAR A LA VISTA (JSP)
        request.getRequestDispatcher("/personajes.jsp").forward(request,response);

    }


}