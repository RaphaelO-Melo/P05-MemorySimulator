/*******************************************************************************
 ** Classe dos blocos usados para memória e para processos, podem conter      **
 ** todos ou apenas parte dos atributos de acordo com a aplicação em que      **
 ** serão usados, originalmente era a classe Nó aplicada em sala              **
 ******************************************************************************/
package simulador.de.memoria;

public class Bloco {

    //Variáveis da classe
    private Bloco prox;
    private boolean ehMemoria;
    private int numProcesso, posInicial, posFinal;

    //Construtor para blocos de processos
    public Bloco(int numProcesso, int iMemoria, int fMemoria, Bloco prox) {
        this.prox = prox;
        this.ehMemoria = false;
        this.posFinal = fMemoria;
        this.posInicial = iMemoria;
        this.numProcesso = numProcesso;
    }

    //Construtor para blocos de memória
    public Bloco(int posInicial, int posFinal, Bloco prox) {
        this.prox = prox;
        this.ehMemoria = true;
        this.posFinal = posFinal;
        this.posInicial = posInicial;
    }
    
    //Getters da classe que retornarão as informações que os métodos necessitam
    //Retorna o número do processo
    public int getNumProcesso() {
        return this.numProcesso;
    }

    //Retorna a posição inicial na memória
    public int getPosInicial() {
        return this.posInicial;
    }

    //Retorna a posição final na memória
    public int getPosFinal() {
        return this.posFinal;
    }
    
    //Retorna o tamanho do bloco em Bytes
    public int getTamanho(){
        return (this.posFinal - this.posInicial);
    }

    //Retorna o próximo bloco na lista
    public Bloco getProx() {
        return this.prox;
    }

    //Setters da classe que definirão novos valores para os atributos do bloco
    //Define o próximo bloco na lista
    public void setProx(Bloco prox) {
        this.prox = prox;
    }
    
    //Define a nova posição inicial
    public void setPosInicial(int posInicial){
        this.posInicial = posInicial;
    }
    
    //Define a nova posição final
    public void setPosFinal(int posFinal){
        this.posFinal = posFinal;
    }
    
    //Reconfigura o bloco, aplicado quando um bloco de memória se une a outro
    public void reconfig (int posInicial, int posFinal, Bloco prox){
        this.prox = prox;
        this.posFinal = posFinal;
        this.posInicial = posInicial;
    }
    
    //Mostra o bloco de acordo com a variável que diz qual tipo de bloco ele é
    @Override
    public String toString() {
        if (ehMemoria){
            return "Endereço inicial da memória: "+this.posInicial+
                   "\nTamanho do bloco: "+ (this.posFinal - this.posInicial);
        }else
            return "Número do processo: "+this.numProcesso+
                   "\nEndereço inicial da memória: "+this.posInicial+
                   "\nTamanho do bloco: "+ (this.posFinal - this.posInicial);
    }
}
