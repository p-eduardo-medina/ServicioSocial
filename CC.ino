#include <SoftwareSerial.h>
#define STEP 4
#define DIR 5
char  dato;
int arco = 10;
SoftwareSerial BTSerial(2, 3); // RX -->tx | TX -->rx

void setup() {
  Serial.begin(9600);
  BTSerial.begin(9600);
  pinMode(STEP, OUTPUT);
  pinMode(DIR, OUTPUT);
}

void loop() {
  delay(100);
  if (BTSerial.available()) {
    dato = BTSerial.read();
    Serial.write(dato);
  }
    switch (dato) 
    {
      case 'A':
        arco = 10;
        break;
      case 'C':
        arco = 20;
        break;
      case 'D':
        arco = 40;
        break;
      case 'E':
        arco = 50;
        break;
    }  
    //Serial.println(arco);
   if(dato == 'F'){    
      Serial.print(arco);
      digitalWrite(DIR, LOW);
      for(int i =0; i<arco;i++)
      {
        digitalWrite(STEP, HIGH);
        delay(2);    
        digitalWrite(STEP, LOW);
        delay(2);
      }
        dato = 'X';
        delay(150);
   }
   if(dato == 'B'){
      digitalWrite(DIR, HIGH);
      for(int i =0; i<arco;i++)
      {
        digitalWrite(STEP, HIGH);
        delay(2);    
        digitalWrite(STEP, LOW);
        delay(2);
      }
        dato = 'X';
        delay(150);
   }
  
 }
