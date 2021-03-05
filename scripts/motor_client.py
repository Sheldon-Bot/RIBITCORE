import sys

print("ready") # This is necessary so the Java watchdog knows that the script has began.

# import motor

# GPIO.setmode(GPIO.BCM)

# left_motor = motor.Motor(23)
# right_motor = motor.Motor(24)
#
# while True:
#     left_motor.set_speed(motor.REVERSE_SLOW)
#     right_motor.set_speed(motor.REVERSE_SLOW)
#
# left_motor.cleanup()
# right_motor.cleanup()
#

while True:
    data = input()
    print(data)
