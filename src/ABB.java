package aed;

import java.util.*;


public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    private int size;
    private Nodo raiz;


    private class Nodo {
        T valor;
        Nodo der;
        Nodo izq;
        Nodo padre;

        Nodo (T val) { 
            valor = val;} 

        // Crear Constructor del nodo
    }

    public ABB() {
        size =0;
        raiz =null;
    }

    public int cardinal() {
        return size;
    }

    public T minimo(){
        if (raiz==null) {
            throw new NoSuchElementException("Conjunto vacío");
        }
        Nodo actual = raiz;
        while (actual.izq != null) {
            actual = actual.izq;
        }
        return actual.valor;
    }
    public Nodo nodoMinimoAbsoluto() {
        Nodo minimo = raiz;
        while (minimo.izq != null) {
            minimo = minimo.izq;
        }
        return minimo;
    }

    public T maximo(){
        if (raiz==null) {
            throw new NoSuchElementException("Conjunto vacío");
        }
        Nodo actual = raiz;
        while (actual.der != null) {
            actual = actual.der;
        }
        return actual.valor;
    }

    public void insertar(T elem){
        if (size ==0) {
            Nodo nuevo = new Nodo(elem);
            raiz = nuevo;
            nuevo.padre = null;
            nuevo.izq=null;
            nuevo.der=null;
            size += 1;
        }
        else {
            if (pertenece(elem)) {}
            else {
                Nodo nuevo = new Nodo(elem);
                Nodo anterior = null;
                Nodo actual = raiz;
                while (actual != null) {
                    if (elem.compareTo(actual.valor)>0) {
                        anterior = actual;
                        actual = actual.der;
                        if (actual == null) {
                            anterior.der = nuevo;}
                        }                      
                    else if (elem.compareTo(actual.valor)<0) {
                        anterior = actual;
                        actual = actual.izq;
                        if (actual == null) {
                            anterior.izq = nuevo;}
                    }
                }
                nuevo.padre = anterior;
                size +=1;
                
            }
        }
    }

    public boolean pertenece(T elem){
        boolean res = false;
        Nodo actual = raiz;
        while (actual != null) {
            if (elem.compareTo(actual.valor)==0) {
            return true;
            }
            else {
                if (elem.compareTo(actual.valor)>0) {
                 actual = actual.der;
            }
              else if (elem.compareTo(actual.valor)<0) {
                   actual = actual.izq;
                }
            }
        }
        return res;
    }

    public void eliminar(T elem) {
        if (size == 1) {
            raiz = null;
            size= size-1;
            return;
     
        }
        Nodo actual = raiz;
        while (elem.compareTo(actual.valor)!=0) {
            if (elem.compareTo(actual.valor)>0) {
                     actual = actual.der;
                 }
            else {
                       actual = actual.izq;
                    }
            }

        //CASO 1: El nodo es una hoja (no tiene hijos)
        if (actual.izq==null && actual.der==(null)) {
            if (actual.valor.compareTo(actual.padre.valor)>0){
                actual.padre.der = null;
            }
            else{
                actual.padre.izq=null;
            }
            size = size -1;
            return;
            }

        // CASO 2: El nodo tiene un hijo
        if ((actual.izq!=null && actual.der==null) || (actual.izq==null && actual.der!=null)) {//(caso2: tiene un hijo) //pueda que este mal
            if (actual.izq !=null && actual.der == null) { // si tiene un hijo a la izquierda
                Nodo hijo = actual.izq;
                actual.valor = hijo.valor;
                actual.der = hijo.der;
                actual.izq=hijo.izq;

                if (hijo.der !=null) {
                    hijo.der.padre = actual;
                }
                if (hijo.izq !=null) {
                    hijo.izq.padre = actual;
                }
            }
            else if (actual.izq ==null && actual.der != null) { // si tiene un hijo a la der
                Nodo hijo = actual.der;
                actual.valor = hijo.valor;
                actual.der = hijo.der;
                actual.izq=hijo.izq;

                if (hijo.der !=null) {
                    hijo.der.padre = actual;
                }
                if (hijo.izq !=null) {
                    hijo.izq.padre = actual;
                }
            }
            size=size-1;
            return;
            }
            
        // CASO 3: El nodo tiene 2 hijos
        if (actual.der != null && actual.izq != null) {

            Nodo minimoDer = actual.der;
            while (minimoDer.izq != null) {
                minimoDer = minimoDer.izq;
            }
            actual.valor = minimoDer.valor;

            if (minimoDer.valor.compareTo(minimoDer.padre.valor) < 0) {
                minimoDer.padre.izq = minimoDer.der;
            } 
            else {
                minimoDer.padre.der = minimoDer.der;
            }
            if (minimoDer.der != null) {
                minimoDer.der.padre = minimoDer.padre;
            }
            size = size-1;
            return;
            }
    }


    public String inorder(Nodo actual){
        if (actual==null) {
            return "";
        }
        else {
            if (actual.valor.compareTo(maximo())==0) {
                return inorder (actual.izq) + actual.valor + inorder(actual.der);
            }
            else {
            return inorder (actual.izq) + actual.valor + "," + inorder(actual.der);}
        }
    }
    public String toString(){
        String res = "{" + inorder(raiz) + "}";
        return res;
        

    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;

        public boolean haySiguiente() {    
            if (_actual.valor.compareTo(maximo())==0) {
                return false;
            }
            else{
                return true;
            } 
        }
        
        public Nodo minimoSubArbol() {
            Nodo min = _actual.der;
            while (min.izq != null) {
                min = min.izq;
            }
            return min;
        }
        
        public T siguiente() {
            if (_actual == null) {
                _actual =nodoMinimoAbsoluto();
            }
            Nodo dedito = _actual;
            if (haySiguiente()) { //sucesor
                if (_actual.der != null) {
                    _actual= minimoSubArbol();
                }
                else {
                while (_actual.padre!=null && (_actual.valor.compareTo(_actual.padre.der.valor)==0)) {
                    _actual = _actual.padre;
                   }
                if (_actual.padre!=null && (_actual.valor.compareTo(_actual.padre.izq.valor)==0)) {
                    _actual = _actual.padre; 
                }
                }
            
            }
            return dedito.valor;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}
