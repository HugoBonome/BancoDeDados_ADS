package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Cafeteira {

    // List<List<String>> linhas = new ArrayList<>();
    // linhas.add(Arrays.asList("123", "juca", "j@email"));
    // escreve(linhas, "usuarios.csv", true);
    public static void escreve(List<List<String>> linhas, String filename, boolean append) {

        try {
            FileWriter arquivo = new FileWriter(filename, append);
            for (List<String> elem : linhas) {
                arquivo.append(String.join(",", elem));
                arquivo.append("\n");
            }
            arquivo.flush();
            arquivo.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // ArrayList<ArrayList<String>> linhas = le(usuarios.csv);
    public static ArrayList<ArrayList<String>> le(String pathname) {
        ArrayList<ArrayList<String>> linhas = new ArrayList<ArrayList<String>>(0);
        try {
            File entrada = new File(pathname);
            Scanner linha = new Scanner(entrada);

            while (linha.hasNext()) {
                String[] registro = linha.nextLine().split(",");

                ArrayList<String> list = new ArrayList<>(Arrays.asList(registro));
                linhas.add(list);
            }

            linha.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return linhas;
    }

    public static String userInfo(int id) {
        String resp = "";
        ArrayList<ArrayList<String>> arquivo = le("/home/aluno/IdeaProjects/cafeteira/src/main/resources/usuario.csv");
        for (List<String> linha : arquivo) {
            if (linha.get(0).equals(String.valueOf(id))) {
                resp = "\t" + linha.get(0) + "," + linha.get(1) + "," + linha.get(2);
            }
        }
        if (resp.equals("")) {
            resp = "\tUsuario não encontrado";
        }

        return resp;
    }

    public static String hist(int id) {
        String resp = "\tHistórico de cafés do usuário id " + id;

        return resp;
    }

    public static String cafInfo() {
        String resp = "\tÓtima cafeteira";

        return resp;
    }

    public static String userAdd(int id) {


        String resp = "\tUsuário de id " + id + " adicionado";

        return resp;
    }

    public static String remUser(int id) {
        String resp = "\tUsuário de id " + id + " removido";

        return resp;
    }

    public static String servir(int id, int tipo) {
        String resp = "\tUsuário de id " + id + " servido café tipo " + tipo;
        if(tipo != 1 && tipo != 2) {
            resp = "\t Tipo de café inválido";
        }



        return resp;
    }

    public static String reabastecer() {
        String resp = "\tReabastecendo cafeteira ...";

        return resp;
    }

    public static void main(String[] args) {
        System.out.println("Cafeteira System");

        boolean continua = true;
        int opcao = 0;
        int id = 0;
        int tipo = 0;

        Scanner in = new Scanner(System.in);

        while (continua) {
            System.out.println("================");
            System.out.println("Digite 1: Para informações de usuário");
            System.out.println("Digite 2: Para histórico de cafés");
            System.out.println("Digite 3: Para informações da cafeteira");
            System.out.println("Digite 4: Para adicionar novo usuário");
            System.out.println("Digite 5: Para remover usuário");
            System.out.println("Digite 6: Para servir café");
            System.out.println("Digite 7: Para reabastecer cafeteira");
            System.out.println("Digite 8: Para sair");
            System.out.print("Sua opção: ");
            opcao = in.nextInt();

            if (opcao == 1) {
                System.out.print("Entre com o id do usuário: ");
                id = in.nextInt();
                String resp = userInfo(id);
                System.out.println(resp);
            } else if (opcao == 2) {
                System.out.print("Entre com o id do usuário: ");
                id = in.nextInt();
                String resp = hist(id);
                System.out.println(resp);
            } else if (opcao == 3) {
                System.out.println("Informações da cafeteira:");
                String resp = cafInfo();
                System.out.println(resp);
            } else if (opcao == 4) {
                System.out.println("Adicionando novo usuário:");
                System.out.print("Entre com o id do usuário: ");
                id = in.nextInt();
                String resp = userAdd(id);
                System.out.println(resp);
            } else if (opcao == 5) {
                System.out.println("Removendo usuário:");
                System.out.print("Entre com o id do usuário: ");
                id = in.nextInt();
                String resp = remUser(id);
                System.out.println(resp);
            } else if (opcao == 6) {
                System.out.println("Servindo café:");
                System.out.print("Entre com o id do usuário: ");
                id = in.nextInt();
                System.out.print("Entre com o tipo de café (1 ou 2): ");
                tipo = in.nextInt();
                String resp = servir(id, tipo);
                System.out.println(resp);
            } else if (opcao == 7) {
                String resp = reabastecer();
                System.out.println(resp);
            } else if (opcao == 8) {
                continua = false;
            }
        }
        in.close();
    }
}