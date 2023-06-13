#include <M5StickCPlus.h>

/* After M5StickC is started or reset
  the program in the setUp () function will be run, and this part will only be run once.
  After M5StickCPlus is started or reset, the program in the setup() function will be executed, and this part will only be executed once. */
void setup(){
  // Initialize the M5StickCPlus object. Initialize the M5StickCPlus object
  M5.begin();

  // LCD display. LCd display
  M5.Lcd.print("Hello World");
}

/* After the program in setup() runs, it runs the program in loop()
The loop() function is an infinite loop in which the program runs repeatedly
After the program in the setup() function is executed, the program in the loop() function will be executed
The loop() function is an endless loop, in which the program will continue to run repeatedly */
void loop() {
}
