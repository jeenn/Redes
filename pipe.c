#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <pthread.h>

int fd[2];

void *lee(){
  while(1){
    char ch;
    int r;
    r=read(fd[0], &ch, 1);
    if(r!=1){
      perror("Error en la funcion read \n");
      exit(1);
    }
    printf("Leido: %c\n", ch);
  }
}

void *escribe_Letra(){//Escribe de la 'A' a la 'Z' con Mayúsuclas
  int r;
  char ch='A';
  while (1) { //Escribe de a 'A' a la 'Z'
    r=write(fd[1], &ch, 1);
    if(r!=1){
      perror("Error en la funcion write \n");
      exit(2);
    }
    printf("Escrito: %c\n", ch);
    if(ch=='Z')
      ch='A'-1; //resetear a la A
      ch++;
  }
}

void *escribe_letra(){//Escribe de la 'a' a la 'z' con minúscula
  int r;
  char ch='a';
  while(1){
    r=write(fd[1], &ch, 1);
    if(r!=1){
      perror("Error en la funcion write \n");
      exit(2);
    }
    printf("Escrito: %c\n", ch);
    if(ch=='a')
      ch='a'-1;
      ct++;
  }
}

int main() {
    pthread t1, t2, t3;
    int result;
    result=pipe(fd);
    if(result<0){
      perror("Error en la funcion pipe \n"):
      exit(3);
    }
    pthread_create(&t1, NULL, lee, NULL);
    pthread_create(&t2, NULL, escribe_Letra, NULL);
    pthread_create(&t2, NULL, escribe_letra, NULL);
    pthread_join(t1,NULL);
    pthread_join(t2,NULL);
    pthread_join(t3,NULL);
}
