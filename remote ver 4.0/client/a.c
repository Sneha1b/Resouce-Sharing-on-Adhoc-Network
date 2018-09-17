#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <semaphore.h>
#include <unistd.h>

#define BUFF_SIZE 5

sem_t full;
sem_t empty;
sem_t mutex;
int buf[BUFF_SIZE];
int in, out;

void* Produce(){
    sem_wait(&empty);
    sem_wait(&mutex);
    int item = in;
    buf[in] = item;
    in = (in+1)%BUFF_SIZE;
    printf("Producing %d\n", item);
    fflush(stdout);
    sem_post(&mutex);
    sem_post(&full);
    return NULL;
} 

void* Consume(){
    sem_wait(&full);
    sem_wait(&mutex);
    int item = buf[out];
    out = (out+1)%BUFF_SIZE;
    printf("Consuming %d\n", item);
    fflush(stdout);
    sem_post(&mutex);
    sem_post(&empty);
    return NULL;
} 

int main()
{
    sem_init(&full, 0, 0);
    sem_init(&empty, 0, BUFF_SIZE);
    sem_init(&mutex,0,1);

    pthread_t idP, idC;
    int index;

    for(index=0; index<10; index++)
    {
        pthread_create(&idC, NULL, Consume, NULL);
    }

    for (index = 0; index < 10; index++)
    {
        pthread_create(&idP, NULL, Produce, NULL);
    }



    pthread_exit(NULL);

}