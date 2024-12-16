import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException, InterruptedException {
        String complementoURL="codes",complementoURLPair="pair";
        int contador=1,opcion=0;
        double cantidad=0.0;
        Scanner entrada=new Scanner(System.in);

        Request codigosRequest= new Request(complementoURL);
        Gson gson = new Gson();
        List<Moneda> listaMonedas = new ArrayList<>();

        HttpResponse<String> responseCodes= codigosRequest.ejecutarRequest();
        String json = responseCodes.body();


        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        Type listType = new TypeToken<ArrayList<ArrayList<String>>>() {}.getType();
        ArrayList<ArrayList<String>> supportedCodes = gson.fromJson(jsonObject.get("supported_codes"), listType);


        for (ArrayList<String> code : supportedCodes) {
            String nombre = code.get(0);
            String paisOrigen = code.get(1);
            listaMonedas.add(new Moneda(nombre, paisOrigen,0.0));
        }

        do {
            // Mostrar opciones disponibles
            System.out.println("\nSelecciona una opción:");
            for (int i = 0; i < listaMonedas.size(); i++) {
                System.out.println((i + 1) + ". Nombre Moneda: " + listaMonedas.get(i).getNombre() +
                        ", País de Origen Moneda: " + listaMonedas.get(i).getPaisOrigen());
            }
            System.out.println((listaMonedas.size() + 1) + ". Salir");

            try {
                System.out.print("Ingrese la moneda a convertir (o seleccione " + (listaMonedas.size() + 1) + " para salir): ");
                opcion = entrada.nextInt();
                if (opcion == listaMonedas.size() + 1) {
                    System.out.println("Saliendo del programa...");
                    break;
                }

                Moneda monedaBase = listaMonedas.get(opcion - 1);


                System.out.print("Ingrese la moneda destino a la cual desea convertir (" + monedaBase.getNombre() + "): ");
                opcion = entrada.nextInt();
                if (opcion == listaMonedas.size() + 1) {
                    System.out.println("Saliendo del programa...");
                    break;
                }

                Moneda monedaDestino = listaMonedas.get(opcion - 1);


                System.out.print("Ingrese la cantidad a convertir: ");
                cantidad = entrada.nextDouble();


                String fullComplementoURL = complementoURLPair + "/" + monedaBase.getNombre() + "/" + monedaDestino.getNombre() + "/" + cantidad;
                Request pairRequest = new Request(fullComplementoURL);

                HttpResponse<String> responseConversion = pairRequest.ejecutarRequest();
                String jsonConversion = responseConversion.body();
                JsonObject jsonObjectConversion = gson.fromJson(jsonConversion, JsonObject.class);

                String monBase = jsonObjectConversion.get("base_code").getAsString();
                String monDes = jsonObjectConversion.get("target_code").getAsString();
                double conversionRate = jsonObjectConversion.get("conversion_rate").getAsDouble();
                double conversionResult = jsonObjectConversion.get("conversion_result").getAsDouble();

                System.out.println(monBase+" -> "+monDes+": ");
                System.out.println(" -> Tasa de Conversión (conversion_rate): " + conversionRate);
                System.out.println(" -> Resultado de Conversión (conversion_result): " + conversionResult);

            } catch (IndexOutOfBoundsException e) {
                System.out.println("Opción inválida. Por favor, intente de nuevo.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (true);

        entrada.close();
    }

}