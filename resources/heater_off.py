import RPi.GPIO as GPIO
import sys


GPIO.setmode(GPIO.BCM)
GPIO.setup(21, GPIO.OUT)
GPIO.setwarnings(False)

def heater_off(heater_pin):
    GPIO.output(heater_pin, GPIO.LOW)
    sys.stdout.write('Heater is off')


if __name__ == "__main__":
    heater_off(21)
