package es.daw.simpson.util;

public class Utils {

    /**
     * Leer un texto y convertirlo a un Integer
     * @param nombreCampo
     * @param valor
     * @return
     * @throws Exception
     */
    public static Integer leerEntero(String nombreCampo, String valor) throws Exception{

        // 1. el valor del campo numérico no esté vacío ni sea nulo
        if (valor == null || valor.isBlank()){
            //return null;
            throw new Exception("El campo "+nombreCampo+" no puede ser nulo ni estar vacío");
        }

        // 2. convierto el campo a número
        // "uno" .... texto que no se pueda convertir a número dará un NumberFormatException
        Integer num;
        try {
            num = Integer.valueOf(valor);
        }catch (NumberFormatException e){
            throw new Exception("El campo "+nombreCampo+" debe ser un número entero");
        }
        // 3. si el número es negativo no lo permito... no hay una edad negativa, por ejemplo
        if (num < 0)
            throw new Exception("El campo "+nombreCampo+" debe ser positivo");


        return num;
    }
}
