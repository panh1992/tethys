package org.athena;

public class Test {


    public static void main(String[] args){

        Class<A> aClass = A.class;



    }

}

class A {

    public static int i = 0;

    public int j = 0;

    public void sayHi(){
        System.out.println("this is A");
    }

    public static void sayHi1(){
        System.out.println("this is A");
    }


}

class B extends A {


    public void sayHi(){
        System.out.println("this is B");
    }


}
class C extends A {


    public void sayHi(){
        System.out.println("this is C");
    }

}

