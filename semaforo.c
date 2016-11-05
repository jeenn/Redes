//Semáforos con nombre: sincronixar hilos o procesos que estén compartiendo memoria

#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <string.h>

void manejador(void *p);

sem_t s;
int cont;
int int main() {
  int i[2];
  pthread_t t1, t2;
  i[0]=0;
  i[1]=1;
  sem_init(&s,0,2);
  pthread_create(&t1, NULL, manejador, (void *)&i[0]);
  pthread_create(&t2, NULL, manejador, (void *)&i[1]);
  pthread_join(t1, NULL);
  pthread_join(t2, NULL);
  sem_destroy(&s);
  exit(0);
}
void manejador(void *p){
  int x,valor;
  x=*((int *)p);//identificador del hilo que ejecuta la funcion manejador
  printf("Hilo %d: esperando a su región crítica\n", x); //id hilo, 0 o 1
  sem_getvalue(&s,&valor); //cantidad de permisos que tiene el hilo
  printf("El valor inicial del semaforo es: %d\n", valor);
  sem_wait(&s);
  sem_getvalue(&s,&valor);
  printf("El valor del semaforo es: %d\n", valor);
  printf("Hilo %d dentro de su región crítica: \n", x);
  printf("Hilo %d modificara el valor del contador que es: %d \n", x, cont);
  cont++;
  printf("Hilo %d actualizo el contador a %d\n", x, cont);
  printf("Hilo%d termina region crítica \n", x);
  sem_pos(&s);
  pthread_exit(0);
}
