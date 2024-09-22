package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.String.valueOf;

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
        ArrayList<ArrayList<String>> arquivo = le("C:/Users/zacar/Documentos/PROG - IFSC/18/BancoDeDados_ADS-main/BancoDeDados_ADS-main/cafeteira/src/main/resources/usuario.csv");
        for (List<String> linha : arquivo) {
            if (linha.get(0).equals(valueOf(id))) {
                resp = "\t" + linha.get(0) + "," + linha.get(1) + "," + linha.get(2);
            }
        }
        if (resp.equals("")) {
            resp = "\tUsuario não encontrado";
        }

        return resp;
    }

    public static String hist(int id) {
        StringBuilder resp = new StringBuilder();
        ArrayList<ArrayList<String>> arquivo = le("C:/Users/zacar/Documentos/PROG - IFSC/18/BancoDeDados_ADS-main/BancoDeDados_ADS-main/cafeteira/src/main/resources/consumo.csv");
        for (List<String> linha : arquivo) {
            if(linha.get(0).equals(valueOf(id))) {
                resp.append("\t-> ").append(linha.get(0)).append(",").append(linha.get(1)).append(",").append(linha.get(2)).append(",").append(linha.get(3)).append(",").append(linha.get(4)).append("\n");
            }
        }
        if (resp.toString().equals("")) {
            resp = new StringBuilder("\tUsuario não encontrado");
        }
        return resp.toString();
    }

    public static String cafInfo() {
        String resp = "";
        ArrayList<ArrayList<String>> arquivo = le("C:/Users/zacar/Documentos/PROG - IFSC/18/BancoDeDados_ADS-main/BancoDeDados_ADS-main/cafeteira/src/main/resources/cafeteira.csv");
        for (List<String> linha : arquivo) {
            resp = "\tQuantidade máxima:" + linha.get(0) + "\n\tQuantidade disponível:" + linha.get(1) + "\n\tPreço da unidade:" + linha.get(2);
        }
        if (resp.equals("")) {
            resp = "\tUsuario não encontrado";
        }

        return resp;
    }

    public static String listarUsuarios() {
        StringBuilder resp = new StringBuilder();
        ArrayList<ArrayList<String>> arquivo = le("C:/Users/zacar/Documentos/PROG - IFSC/18/BancoDeDados_ADS-main/BancoDeDados_ADS-main/cafeteira/src/main/resources/usuario.csv");
        for (List<String> linha : arquivo) {
                resp.append("\t->").append(linha.get(0)).append(",").append(linha.get(1)).append(",").append(linha.get(2)).append("\n");
        }
        if (resp.toString().equals("")) {
            resp = new StringBuilder("\tUsuario não encontrado");
        }

        return resp.toString();
    }

    public static String userAdd(int id, String nome, String email, String saldo) {
        ArrayList<ArrayList<String>> arquivo = le("C:/Users/zacar/Documentos/PROG - IFSC/18/BancoDeDados_ADS-main/BancoDeDados_ADS-main/cafeteira/src/main/resources/usuario.csv");
        for (List<String> linha : arquivo) {
            if (linha.get(0).equals(valueOf(id))) {
                return "Usuário não cadastrado - Já existe um usuário com esse id";
            }
        }
        List<List<String>> linhas = new ArrayList<>();
        linhas.add(Arrays.asList(String.valueOf(id), nome, email, saldo));
        escreve(linhas, "C:/Users/zacar/Documentos/PROG - IFSC/18/BancoDeDados_ADS-main/BancoDeDados_ADS-main/cafeteira/src/main/resources/usuario.csv", true);
        String resp = "\tUsuário de id " + id + " adicionado";
        return resp;
    }

    public static String remUser(int id) {
        String resp = "\tUsuário de id " + id + " removido";

        return resp;
    }

    public static String servir(int id, int tipo) {
        String type = "";
        String[] cafInfo = cafInfo().split(",");
        String[] userInfo = userInfo(id).split(",");
        List<List<String>> linhasConsumo = new ArrayList<>();
        List<List<String>> linhasCafeteira = new ArrayList<>();

        //Verifica se o cliente existe
        boolean m = false;
        ArrayList<ArrayList<String>> arquivoClientes = le("C:/Users/zacar/Documentos/PROG - IFSC/18/BancoDeDados_ADS-main/BancoDeDados_ADS-main/cafeteira/src/main/resources/usuario.csv");
        for (List<String> linha : arquivoClientes) {
            if(linha.get(0).equals(valueOf(id))){
                m = true;
            }
        }
        if (!m){
            return "usuario não encontrado!";
        }

        //Verifica se o tipo escolhido é válido
        if(tipo != 1 && tipo != 2) {
            return "\t Tipo de café inválido";
        }

        //Verifica a quantidade de cafés disponíveis
        int qtdDisponivel = Integer.parseInt(cafInfo[1]);
        int qtdServida = tipo;
        if(qtdDisponivel < qtdServida ){
            return "Quantidade de café indisponível";
        }

        //verifica se o cliente tem saldo
        double preco = Double.parseDouble(cafInfo[2]) * tipo;//multiplicando o valor unitário do Café pelo tipo/quantidade vendida
        double saldo = Double.parseDouble(userInfo[3]);
        if (preco > saldo){
            return "Cliente com saldo indisponível";
        }


        //Retirando quantidade da cafeteira
        int qtdResult = qtdDisponivel - qtdServida;
        cafInfo[1] = valueOf(qtdResult);
        linhasCafeteira.add(Arrays.asList(cafInfo[0],cafInfo[1],cafInfo[2]));
        escreve(linhasCafeteira, "C:/Users/zacar/Documentos/PROG - IFSC/18/BancoDeDados_ADS-main/BancoDeDados_ADS-main/cafeteira/src/main/resources/consumo.csv", false);

        //Adicionando Consumo ao arquivo
        if (tipo == 1){
            type = "simples";
        } else {
            type = "duplo";
        }
        Date data = new Date();
        linhasConsumo.add(Arrays.asList(userInfo[0],userInfo[1], userInfo[2],type, data.toString()));
        escreve(linhasConsumo, "C:/Users/zacar/Documentos/PROG - IFSC/18/BancoDeDados_ADS-main/BancoDeDados_ADS-main/cafeteira/src/main/resources/consumo.csv", true);

        //Ajustando saldo do cliente
        double saldoResult = saldo - preco;
        List<List<String>> linhas = new ArrayList<>();
        for (List<String> linha : arquivoClientes) {
            if(linha.get(0).equals(valueOf(id))){
                linhas.add(Arrays.asList(linha.get(0), linha.get(1), linha.get(2), valueOf(saldoResult)));
            } else {
                linhas.add(Arrays.asList(linha.get(0),linha.get(1),linha.get(2),linha.get(3)));
            }
        }
        escreve(linhas, "C:/Users/zacar/Documentos/PROG - IFSC/18/BancoDeDados_ADS-main/BancoDeDados_ADS-main/cafeteira/src/main/resources/usuario.csv", false);


        return "\tUsuário de id " + id + " servido café tipo " + type;
    }

    public static String reabastecer() {
        List<List<String>> linhas = new ArrayList<>();
        String[] cafInfo = cafInfo().split(",");
        linhas.add(Arrays.asList(cafInfo[0],cafInfo[0],cafInfo[2]));

        escreve(linhas,"C:/Users/zacar/Documentos/PROG - IFSC/18/BancoDeDados_ADS-main/BancoDeDados_ADS-main/cafeteira/src/main/resources/cafeteira.csv",false );

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
            System.out.println("Digite 4: Para listar usuários");
            System.out.println("Digite 5: Para adicionar novo usuário");
            System.out.println("Digite 6: Para remover usuário");
            System.out.println("Digite 7: Para servir café");
            System.out.println("Digite 8: Para reabastecer cafeteira");
            System.out.println("Digite 9: Para sair");
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
                System.out.println("Lista de usuários:");
                System.out.println(listarUsuarios());
            } else if (opcao == 5) {
                System.out.println("Adicionando novo usuário:");
                System.out.print("Insira o id do usuário: ");
                id = in.nextInt();
                in.nextLine();
                System.out.println("Insira o nome do usuário:");
                String nome = in.nextLine();
                System.out.print("Insira o email do usuário:");
                String email = in.nextLine();
                System.out.print("Insira o saldo do usuário:");
                String saldo = in.nextLine();

                String resp = userAdd(id, nome, email, saldo);
                System.out.println(resp);
            } else if (opcao == 6) {
                System.out.println("Removendo usuário:");
                System.out.print("Entre com o id do usuário: ");
                id = in.nextInt();
                String resp = remUser(id);
                System.out.println(resp);
            } else if (opcao == 7) {
                System.out.println("Servindo café:");
                System.out.print("Entre com o id do usuário: ");
                id = in.nextInt();
                System.out.print("Entre com o tipo de café (1 ou 2): ");
                tipo = in.nextInt();
                String resp = servir(id, tipo);
                System.out.println(resp);
            } else if (opcao == 8) {
                String resp = reabastecer();
                System.out.println(resp);
            } else if (opcao == 9) {
                continua = false;
            }
        }
        in.close();
    }
}