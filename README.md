# Sheldon Bot

This is the repository for all code running on the Sheldon Bot.

# Requirements

To run Sheldon Bot, you must have the following prerequisites installed:

- Python 3 (Latest is recommended)
- Python venv
- Java 11+
- [opencv-python](https://pypi.org/project/opencv-python/)
- A Raspberry Pi 3B+ running Raspian (may work on other Debian variants). (May work better on Pi 4, yet to be tested)

# Compiling

To compile Sheldon Bot, you must have Java 11+ and Maven.

Clone the repository, and run `maven clean install`. This will create a `sheldon-x.x.x.jar` file in the `target/` directory.

# Running

To run Sheldon Bot, ensure that all prerequisites are present, and copy `sheldon-x.x.x.jar` to the Raspberry Pi.

Run Sheldon Bot by executing `java -jar ${SHELDON_BOT_PATH}` (of course, replacing `${SHELDON_BOT_PATH}` with the path of `sheldon-x.x.x.jar`).

This will, by default, initialize the Python environment, install the necessary packages, and begin running the Python scripts.