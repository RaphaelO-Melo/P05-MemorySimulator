/*******************************************************************************
 ** Classe da lista ligada, aqui serão criadas e manipuladas as duas listas   **
 ** (blocos livres e blocos alocados) do programa                             **
 ******************************************************************************/
package simulador.de.memoria;

public class ListaLigada {
    //As duas listas começam com um bloco, porém a de memória começa com um
    //bloco do tamanho da sua memória, enquanto a de processo começa com null
    private Bloco inicio;
  
    //Construtor para lista de blocos de memória
    public ListaLigada(int memoria) {
        this.inicio = new Bloco(0,memoria,null);
    }
    //Construtor para lista de blocos de processo
    public ListaLigada() {
        this.inicio = null;
    }

    //Adiciona um processo no final da lista
    public void addProcesso(int numProc, int qtdMem, ListaLigada listaMemoria) {
        //Antes de prosseguir, verifica se o processo ocupa no mínimo 1 byte
        if (qtdMem <= 0) {
            System.out.println("O processo precisa ocupar no mínimo 1 byte de "
                    + "memória!");
            return;
        }
        //Um bloco auxiliar armazena o bloco no inicio da lista dos blocos de 
        //memoria, ele será utilizado para andar na lista de blocos de memória e
        //verificar se existe um bloco com o tamanho compatível para o processo
        Bloco auxMemoria = listaMemoria.getInicio();
        boolean achouBloco = false;
        //Loop que vai andar pela lista de memória procurando o bloco livre
        boolean run = true;
        while (run) {
            //Se o tamanho do bloco de memória atual for compatível, cria o 
            //processo e reduz o tamanho desse bloco de memória que foi 
            //utilizado, caso contrário procura o próximo bloco de memória na 
            //lista
            if (qtdMem <= auxMemoria.getTamanho()) {
                //Nesse loop o bloco anda até o final da lista para nela 
                //armazenar o novo bloco de processo
                Bloco auxProcesso = this.inicio;
                Bloco ant = null;
                while (auxProcesso != null) {
                    ant = auxProcesso;
                    auxProcesso = auxProcesso.getProx();
                }
                //Cria o novo bloco de processo que será adicionado
                Bloco novo = new Bloco(numProc, auxMemoria.getPosInicial(),
                        auxMemoria.getPosInicial() + qtdMem, auxProcesso);
                //Verifica se o anterior está nulo, com essa informação é 
                //possivel entender que a lista está vazia, então o bloco sera 
                //adicionado no começo da lista, caso não seja o primeiro, 
                //é adicionado no final da lista
                if (ant == null) {
                    this.inicio = novo;
                } else {
                    ant.setProx(novo);
                }
                //Após criar o novo bloco e adicionar na lista de processos,
                //agora é redimensionado o tamanho do bloco de memória que foi
                //utilizado
                auxMemoria.setPosInicial(novo.getPosFinal());
                //Caso o tamanho do bloco tenha ficado igual a 0, é removido da
                //lista de blocos de memória
                if (auxMemoria.getTamanho() == 0) {
                    removeBlocoMemoria(auxMemoria.getPosInicial(),
                            auxMemoria.getPosFinal(), listaMemoria);
                }
                achouBloco = true;
                run = false;
            }

            //Permite andar apenas até o final da lista de blocos de memória
            if (auxMemoria.getProx() != null) {
                auxMemoria = auxMemoria.getProx();
            }
            //Se chegou até o final da lista, o tamanho atual não é compatível
            //com os blocos de memória e não achou nenhum bloco, sinaliza a
            //aplicação cliente e finaliza o loop
            if (auxMemoria.getProx() == null && qtdMem > auxMemoria.getTamanho()
                    && !achouBloco) {
                System.out.println("Não existe bloco que suporte este processo!");
                run = false;
            }
        }
    }

    //Finaliza o processo escolhido pelo usuário e cria um novo bloco de memória
    //com o espaço que ele ocupava
    public void removeProcesso(int ref, ListaLigada listaMemoria) {
        //Anda pela lista de processos procurando o processo escolhido
        Bloco auxProcesso = this.inicio;
        Bloco antProcesso = null;
        boolean achou = false;
        //Anda até o último elemento da lista
        while (auxProcesso != null) {
            //Se o número do processo for igual ao número do processo atual, 
            //achou o processo, então chama a função que cria o bloco de memória
            //com o tamanho deste processo
            if (auxProcesso.getNumProcesso() == ref) {
                listaMemoria.addMemoria(auxProcesso.getPosInicial(), 
                        auxProcesso.getPosFinal(), listaMemoria);
                //Remove as referências do processo finalizado
                if (antProcesso != null) {
                    antProcesso.setProx(auxProcesso.getProx());
                }
                if (antProcesso == null){
                    this.inicio = auxProcesso.getProx();
                }
                //Após isso, chama a função que organiza a memória
                organizaMemoria(listaMemoria);
                //informa que achou o processo
                achou = true;
            }
            //Anda pela lista
            antProcesso = auxProcesso;
            auxProcesso = auxProcesso.getProx();
        }
        //Se chegar ao final da lista e não achou o processo, retorna e informa
        //a aplicação cliente
        if (!achou) {
            System.out.println("O processo não existe!");
        }
    }
    
    //Adiciona um bloco de memória de forma ordenada pelo endereço inicial
    public void addMemoria(int posIni, int tamanho, ListaLigada listaMemoria) {
        //Variável que guarda a posição inicial da lista de blocos de memória     
        Bloco aux = listaMemoria.getInicio();
        Bloco ant = null;
        //Loop que anda na lista até achar um auxiliar com o inicio da memória 
        //maior que o do bloco que será criado
        while (aux != null && aux.getPosInicial() < posIni) {
            ant = aux;
            aux = aux.getProx();
        }
        //Cria o bloco de memória que será adicionada
        Bloco novo = new Bloco(posIni, tamanho, aux);
        //Se o anterior for null, adiciona no começo da lista
        if (ant == null) {
            listaMemoria.inicio = novo;
        } else {
            ant.setProx(novo);
        }
    } 
    
    //Organiza a lista de blocos de memória, para isso os deixa ordenado de
    //forma crescente através de sua posição inicial na memória
    public void organizaMemoria(ListaLigada listaMemoria) {
        //Para organizar a memória é necessário existirem pelo menos dois blocos
        //de memória
        if (listaMemoria.getInicio().getProx() != null) {
            boolean achou;
            //O método começa com duas variáveis, uma na primeira posição da 
            //lista, e outra na segunda
            Bloco x1 = listaMemoria.getInicio();
            Bloco x2 = listaMemoria.getInicio().getProx();

            //Anda enquanto x2 não for nulo, como já está organizada, as duas 
            //referências podem andar juntas
            while (x2 != null) {
                achou = false;
                //Se a posição final do primeiro bloco, for igual à posição 
                //inicial do próximo, são contíguos
                if (x1.getPosFinal() == x2.getPosInicial()) {
                    achou = true;
                    //Então é criado um novo bloco de memória, o mesmo será  
                    //posicionado na lista de acordo com sua posInicial
                    listaMemoria.addMemoria(x1.getPosInicial(), 
                            x2.getPosFinal(), listaMemoria);
                    //Após isso, os dois blocos que formaram o novo bloco são
                    //removidos da lista de blocos de memória
                    removeBlocoMemoria(x1.getPosInicial(), x1.getPosFinal(), 
                            listaMemoria);
                    removeBlocoMemoria(x2.getPosInicial(), x2.getPosFinal(), 
                            listaMemoria);
                }
                //Se achou os blocos contíguos, um novo bloco foi adicionado na
                //lista de blocos, então os auxiliares são novamente deslocados
                //para as posições iniciais da lista
                if (achou) {
                    x1 = listaMemoria.getInicio();
                    x2 = listaMemoria.getInicio().getProx();
                }
                //Caso ainda não tenha achado blocos contíguos, continua andando
                if (!achou) {
                    x1 = x1.getProx();
                    x2 = x1.getProx();
                }
            }
        }
    }
    
    //Remove um bloco de memória, para isso anda pela lista procurando o bloco
    //com mesmo endereço inicial e final
    public void removeBlocoMemoria(int ini, int fim, ListaLigada listaMemoria) {
        for (Bloco ant = null, aux = listaMemoria.getInicio(); aux != null; ant = aux, aux = aux.getProx()) {
            if (aux.getPosInicial() == ini && aux.getPosFinal() == fim) {
                //Se o anterior não for nulo, no anterior seta como próximo o
                //próximo do bloco que será removido, assim ele para de ser
                //referenciado na lista
                if (ant != null){
                    ant.setProx(aux.getProx());
                }
                //Caso o anterior seja nulo, significa que é o primeiro bloco
                //da lista, sendo assim, agora o início passa a ser o segundo
                if (ant == null){
                    listaMemoria.inicio = aux.getProx();
                }
            }
        }
    }
    
    //Imprime os Processos
    public String mostraProcessos() {
        String resp = "--------------------------------------\n";
        Bloco aux = this.inicio;
        while (aux != null) {
            resp += aux.toString() + "\n--------------------------------------\n";
            aux = aux.getProx();
        }
        return resp;
    }

    //Imprime os blocos de memória
    public String mostraMemoria() {
        String resp = "--------------------------------------\n";
        Bloco aux = this.inicio;
        while (aux != null) {
            resp += aux.toString() + "\n--------------------------------------\n";
            aux = aux.getProx();  
        }
        return resp;
    }
    
    //Retorna o início da lista
    public Bloco getInicio() {
        return this.inicio;
    }
    
    //Devolve para a aplicação cliente a quantidade de memória disponível, para
    //isso anda pela lista somando o tamanho dos blocos de memória disponíveis
    public int getMemoria() {
        int resp = 0;
        //Guarda bloco inicial de memória
        Bloco aux = this.inicio;
        while (aux != null) {
            resp += aux.getTamanho();
            aux = aux.getProx();
        }
        //após andar por toda a lista, retorna o valor obtido
        return resp;
    }
}





    
