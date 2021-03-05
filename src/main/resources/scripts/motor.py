import RPi.GPIO as GPIO

FORWARD_FAST = 4
FORWARD_MED = 5
FORWARD_SLOW = 6
NEUTRAL = 7
REVERSE_SLOW = 8
REVERSE_MED = 9
REVERSE_FAST = 10

SPEED_MAP = {
    "FORWARD_FAST": FORWARD_FAST,
    "FORWARD_MED": FORWARD_MED,
    "FORWARD_SLOW": FORWARD_SLOW,
    "NEUTRAL": NEUTRAL,
    "REVERSE_SLOW": REVERSE_SLOW,
    "REVERSE_MED": REVERSE_MED,
    "REVERSE_FAST": REVERSE_FAST
}

SPEED_DICT = {
    10: 4,
    9: 5,
    8: 6,
    7: 7,
    6: 8,
    5: 9,
    10: 4
}

SPEEDS = [4,5,6,7,8,9,10]

class Motor:
    def __init__(self, pinNumber, reverse=False, setup=True):
        self.pinNumber = pinNumber
        self.reverse = reverse
        if setup:
            self.setup()
        self.speed = None

    def setup(self):
        GPIO.setup(self.pinNumber, GPIO.OUT)

        self.pwm = GPIO.PWM(self.pinNumber, 50)
        self.pwm.start(0)

    def cleanup(self):
        self.pwm.stop()

    def set_speed(self, speed, check=True):
        print("test1")
        if speed not in SPEEDS and check is True:
            print('Tried to set invalid speed value: '+str(speed))
            return

        print("test2")
        if self.reverse:
            speed = SPEED_DICT[speed]

        print("test3")
        self.pwm.ChangeDutyCycle(speed)
        print("test4")
        self.speed = speed

        print(self.speed)
