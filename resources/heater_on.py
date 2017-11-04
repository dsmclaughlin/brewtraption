import RPi.GPIO as GPIO
import sys


GPIO.setmode(GPIO.BCM)
GPIO.setup(21, GPIO.OUT)
GPIO.setwarnings(False)

def heater_on(heater_pin):
    GPIO.output(heater_pin, GPIO.HIGH)
    sys.stdout.write('Heater is on')


if __name__ == "__main__":
    heater_on(21)
