/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rotulacao;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Pablo
 */
public class Rotulacao {
    
    /**
     * @param args the command line arguments
     */
    BufferedImage abreImagem() throws IOException{
        BufferedImage image = ImageIO.read(new File("Imagens-imput\\2.jpg"));
        return image;
    }
    
    BufferedImage Binariza(BufferedImage image) {   
        int preto = Color.BLACK.getRGB();   
        int branco = Color.WHITE.getRGB();   
        BufferedImage aux = new BufferedImage(image.getWidth(),image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);      
        for (int i = 0; i < image.getHeight(); i++)   
            for (int j = 0; j < image.getWidth(); j++) {   
                Color pixel = new Color(image.getRGB(j, i));   
                aux.setRGB(j, i, pixel.getRed() < 127 ? preto : branco);   
            }   
        return aux;   
    }   
    public int[][] Image_To_Matriz(BufferedImage ImagemCarregada){
        int altura, largura;
        altura = ImagemCarregada.getHeight();
        largura = ImagemCarregada.getWidth();
        int vetAuxPixel[] = new int[largura*altura];
        vetAuxPixel = ImagemCarregada.getRGB(0, 0, largura, altura, null, 0, largura);
        int matrizPixel[][] = new int[altura][largura];
        int count = 0;
        for (int i=0;i<altura;i++)
        {
            for (int j=0;j<largura;j++)
            {
                matrizPixel[i][j]= vetAuxPixel[count];
                count++;
            }
        }
        return matrizPixel;
    }
    public void rotulacao(int[][] matrizBinaria){
        String[][] matrizRotulos = new String[matrizBinaria.length][matrizBinaria[1].length];
        char rotuloInicial= 'A';
        for(int i=0;i<matrizBinaria.length;i++){
            for(int j=0;j<matrizBinaria[1].length;j++){
                if((matrizBinaria[i][j]*(-1))<127){
                    matrizBinaria[i][j]=0;
                }else {
                    matrizBinaria[i][j]=1;
                }  
            }
        }
        for(int i=0;i<matrizBinaria.length;i++){
            for(int j=0;j<matrizBinaria[1].length;j++){
                if(matrizBinaria[i][j]==1){
                    try {
                        if(matrizBinaria[i][j-1]==0 && matrizBinaria[i-1][j]==0){
                            matrizRotulos[i][j]= Character.toString(rotuloInicial);
                            rotuloInicial++;
                        }
                        if(matrizBinaria[i][j-1]==1 || matrizBinaria[i-1][j]==1){
                            if(matrizBinaria[i][j-1]==1){
                                matrizRotulos[i][j]=matrizRotulos[i][j-1];
                            }
                            else{
                                matrizRotulos[i][j]=matrizRotulos[i-1][j];
                            }
                        }
                        if(matrizBinaria[i][j-1]==1 && matrizBinaria[i-1][j]==1){
                            if(matrizRotulos[i][j-1]==matrizRotulos[i-1][j]){
                                matrizRotulos[i][j]= matrizRotulos[i][j-1];
                            }
                            else if(matrizRotulos[i][j-1]!=matrizRotulos[i-1][j]){
                                if((matrizRotulos[i][j-1]!=null)&&(matrizRotulos[i-1][j]!=null)){
                                    System.out.println("Labels equivalente> "+matrizRotulos[i-1][j]+"="+matrizRotulos[i][j-1]);
                                    matrizRotulos[i][j-1]=matrizRotulos[i-1][j];
                                    matrizRotulos[i-1][j]=matrizRotulos[i][j-1];
                                    matrizRotulos[i][j]=matrizRotulos[i][j-1];
                                }
                            }
                        }
                    } catch (Exception e) {
                        if((j-1)<0 && (i-1)<0){
                            matrizRotulos[i][j]= Character.toString(rotuloInicial);
                            rotuloInicial++;
                        }
                    }
                }
            }
        }
        for(int i=0;i<matrizBinaria.length;i++){
            for(int j=0;j<matrizBinaria[1].length;j++){
                if(matrizRotulos[i][j]==null){
                    System.out.print("-");
                }else{
                    System.out.print(matrizRotulos[i][j]);
                }
            }
            System.out.println(" ");
        }
    }
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        // int[][] matriz = {{1,1,0,0,0,0,0},
        //                   {0,1,1,0,0,0,0},
        //                   {0,0,0,1,0,0,0},
        //                   {0,0,0,1,1,0,1},
        //                   {0,0,0,0,0,1,1},
        //                   {0,0,0,0,0,1,1}};
        Rotulacao aux = new Rotulacao();
        //aux.rotulacao(matriz);
        aux.rotulacao(aux.Image_To_Matriz(aux.Binariza(aux.abreImagem())));
    }
}
