package com.example.taller_2;

import java.io.IOException;
import java.util.StringTokenizer;

public class FilePlane extends FilePersistence{
    //Esdte metodo es que por medio de tokens (lienas) agregra a un arrgelo y restorna ese arreglo
    public String []getContentFile() throws IOException{
        String content = readFile();
        StringTokenizer Lines = new StringTokenizer(content, "\n");
        String[] outPut = new String[Lines.countTokens()];
        int cont=0;
        while (Lines.hasMoreTokens()) {
            outPut[cont ++] = Lines.nextToken();
        }
        return outPut;
    }

    // este metodo recibe un arreglo y los escribe en el archivo de texto
    public void setContentFile(String...content) throws IOException{
        StringBuilder dump = new StringBuilder();
        for(String line: content) {
            dump.append(line + "\n");
        }
        writeFile(dump.toString());
    }
}
