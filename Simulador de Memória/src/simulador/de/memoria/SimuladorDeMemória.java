/*******************************************************************************
 **  Centro Universitário SENAC                                               **
 **  Tecnologia em jogos digitais - 3º Semestre                               **
 **  Fabio Aparecido Gamarra Lubacheski                                       **
 **                                                                           **
 **  Estrutura de dados - T2: Simulador de Gerenciamento de Memória           **
 **                                                                           **
 **  Raphael Oliveira Melo                                                    **
 **  Nathan André da Silva                                                    **
 **                                                                           **
 **  27/05/2019                                                               **
 ******************************************************************************/
package simulador.de.memoria;
import java.util.Scanner;

public class SimuladorDeMemória {
    //Variáveis estáticas para a aplicação
    static int contProcessos= 0;
    static Scanner scan = new Scanner (System.in);
    //As listas podem receber dois parâmetros: 
    //Um int para inicializar a de memória (o int define o tamanho da memória)
    //Nenhum para o caso da lista ser a lista de processos 
    static ListaLigada listaProcesso = new ListaLigada();
    static ListaLigada listaMemoria = new ListaLigada(4000);

    public static void main(String[] args) {
        start();
        operation();
    }

    //Inicializa cabeçalho do programa
    private static void start() {
        System.out.println("**********************************************"
                         + "\n*                                            *"
                         + "\n*            SIMULADOR DE MEMÓRIA            *"
                         + "\n*                                            *"
                         + "\n**********************************************");
    }

    //Função onde ocorre o loop do programa, que roda de acordo com as escolhas
    //tomadas pelo usuário
    private static void operation() {
        int escolha;
        boolean run = true;
        System.out.print("\nEscolha uma das operações: \n"
                + "Memória disponível: " + listaMemoria.getMemoria()+"Bytes\n\n"
                + "1 - Alocar memória para execução do processo;\n"
                + "2 - Finalizar processo;\n"
                + "3 - Mostrar situação atual da memória;\n"
                + "4 - Finalizar o simulador\n");

        //Aguarda o usuário escolher uma ação
        while (run) {
            escolha = scan.nextInt();
            //Trata a escolha de acordo com a opção selecionada
            switch (escolha) {
                case 1:
                    run = false;
                    op1();
                    break;
                case 2:
                    run = false;
                    op2();
                    break;
                case 3:
                    run = false;
                    op3();
                    break;
                case 4:
                    run = false;
                    break;
                default:
                    System.out.println("Reposta inválida, escolha uma das"
                                     + " operações listadas acima.");
                    break;
            }
        }
    }

    //Operação 1: Adiciona um processo na lista de memória alocada
    private static void op1() {
        System.out.println("Digite a quantidade de memória que deseja alocar:");
        int quantMemoria = scan.nextInt();
        listaProcesso.addProcesso(++contProcessos, quantMemoria, listaMemoria);
        operation();
    }

    //Operação 2: Finaliza um processo escolhido pelo usuário
    private static void op2() {
        if (listaProcesso.getInicio() == null) {
            System.out.println("Nenhum processo iniciado! ");
            operation();
        }
        System.out.println(listaProcesso.mostraProcessos() + 
                           "Escolha o processo que deseja finalizar: \n");
        int resp = scan.nextInt();
        listaProcesso.removeProcesso(resp, listaMemoria);
        operation();
    }

    //Operação que imprime as listas:
    private static void op3() {
        System.out.println("LISTA DE BLOCOS LIVRES: \n"
                + listaMemoria.mostraMemoria());
        System.out.println("LISTA DE BLOCOS ALOCADOS: \n"
                + listaProcesso.mostraProcessos());
        operation();
    }
}
