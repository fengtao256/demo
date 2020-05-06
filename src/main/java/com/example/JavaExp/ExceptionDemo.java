package com.example.JavaExp;
import javax.swing.JOptionPane ;
class LowException extends Exception {
    LowException(int i) {
        System.out.println("分数为负数");
    }
}
class HightException extends Exception {
    HightException(int i) {
        System.out.println("分数过高");
    }
}
public class ExceptionDemo {
    static final int number = 2 ;
    int score[] = new int[number] ;
    public void checkScore(int score) throws LowException,HightException {
        if(score > 100){
            throw  new HightException(score) ;
        }
        if(score < 0){
            throw  new LowException(score) ;
        }
    }
    public void inScore(){ //录入成绩
        for (int i = 0; i < number; i++) {
            try{
                score[i] = Integer.parseInt(JOptionPane.showInputDialog("请输入第"+(i+1)+"个同学的成绩"));
            }catch(NumberFormatException e){

            }
            try{
                checkScore(score[i]);
            }catch (HightException e){
                System.out.println(e);
            }catch (LowException e) {
                System.out.println(e);
            }
        }
    }
    public void printScore(){
        for (int eachScore:score) {
            System.out.println(eachScore);
        }
    }

    public static void main(String[] args) {
        ExceptionDemo demo = new ExceptionDemo() ;
        demo.inScore();
        demo.printScore();
    }
}
