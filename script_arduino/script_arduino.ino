#include <dht.h>
#include <LiquidCrystal_I2C.h>
#include <SoftwareSerial.h>
#include <string.h>


SoftwareSerial Bluetooth(6, 7); // RX | TX
 

// Set the LCD address to 0x27 for a 16 chars and 2 line display
LiquidCrystal_I2C lcd(0x27, 16, 2);


dht DHT;
int lampe=13;
int porteIn2=11;
int porteIn1=12;
int fenetreIn4=10;
int fenetreIn3=9;
int dht_pin=8;
bool windowClosed=false;

unsigned long previous = millis();


typedef struct {
  int lampe;
  int porte;
  int fenetre;
} Etat;
Etat e;

void fermePorte(){
  digitalWrite(porteIn1,HIGH);
    digitalWrite(porteIn2,LOW);
    delay(5270);
    digitalWrite(porteIn1,LOW);
}
void ouvrePorte(){
  digitalWrite(porteIn2,HIGH);
    digitalWrite(porteIn1,LOW);
    delay(5000);
    digitalWrite(porteIn2,LOW);
}
void ouvreFenetre(){
  digitalWrite(fenetreIn3,HIGH);
  digitalWrite(fenetreIn4,LOW);
  delay(2500);
  digitalWrite(fenetreIn3,LOW);  
}
void fermeFenetre(){
  digitalWrite(fenetreIn4,HIGH);
  digitalWrite(fenetreIn3,LOW);
  delay(2750);
  digitalWrite(fenetreIn4,LOW);
  windowClosed=true;
}

void allumeLampe(){
  digitalWrite(lampe,HIGH);
  
}

void eteindLampe(){
  digitalWrite(lampe,LOW);


}
float updateTempHumd(){
  int chk = DHT.read11(dht_pin); 
  Bluetooth.print("000sep");
  Bluetooth.print(DHT.temperature);
  Bluetooth.print("sep");
  Bluetooth.print(DHT.humidity);
  Bluetooth.print("sep");
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Temperature :");
  lcd.print((int)DHT.temperature);
  lcd.print("C");
  lcd.setCursor(0, 1);
  lcd.print("humidite:");
  lcd.print((int)DHT.humidity);
  lcd.print("%");
}
void envoyerDonnees(){
  int chk = DHT.read11(dht_pin);
  Bluetooth.flush();
  Bluetooth.print((int)DHT.temperature);
  //Bluetooth.flush();
  Bluetooth.print((int)DHT.humidity);
 

}
void setup() {
  // put your setup code here, to run once:
  pinMode(lampe,OUTPUT);
  pinMode(porteIn2,OUTPUT);
  pinMode(porteIn1,OUTPUT);
  pinMode(fenetreIn4,OUTPUT);
  pinMode(fenetreIn3,OUTPUT);
  pinMode(dht_pin,INPUT);
  lcd.begin();
  lcd.print("initializing ...");
  Bluetooth.begin(9600);
  Serial.begin(9600);
  // Send test message to other device
  //BT.println("Hello from Arduino");
  // Turn on the blacklight
  // lcd.setBacklight((uint8_t)1);
  // lcd.autoscroll();
  // lcd.print("ON");
  // delay(2000);
  // lcd.clear();
    // // Second row
  // lcd.setCursor(0,1);
  updateTempHumd();

  

  
}



void loop() {
  unsigned long current = millis();
  if(Bluetooth.available()){
    
    int a = Bluetooth.read();
    
    
    switch(a){
      case 1: ouvrePorte(); break;
      case 2: fermePorte(); break;
      case 3: ouvreFenetre(); break;
      case 4: fermeFenetre(); break;
      case 5: allumeLampe(); break;
      case 6: eteindLampe(); break;
      case 7: envoyerDonnees(); break;
    } 
  }
  if(current - previous > 1500){
    previous = current;
    float temp = updateTempHumd();
    if(temp > 29 && !windowClosed){
      //ouvreFenetre();
      //windowClosed=true;
    }
  }

  
}

