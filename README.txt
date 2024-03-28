Overview:
Purpose of model is to construct a schedule planner system that can allow a visual representation
of a digital schedule with detailed events containing the name of the event, starting and ending
time of the event, location of the event, and attendees of the event. The schedule is organized as
a one-week calendar from Sunday to Saturday with event placed between these days. However, events
are allowed to end the following week but cannot begin in a week outside the week designated by
the calendar. Additionally, new weeks cannot be creating by the schedule system. The purpose of the
schedule is to help organize events, allowing the addition, removal, and modification of events
to the schedule planner. There are no overlaps allowed between events in the schedule planner.

Quick Start:
ScheduleSystemController myPlanner = new ScheduleSystemController(List<Schedule> listOfSchedules);
Above initializes the schedule planner. Below will show how to read an XML file to add a new
schedule to the planner and how to write an XML file to generate a new XML file for an
existing schedule in the system.
myPlanner.readXML(filePath);
myPlanner.writeXML(newSchedule);
Current system does not support a GUI, only a text representation of the planner for now.

Key Components:
3 main components: model, controller, view.
Model handles all functionality of the Schedule planner, including adding new events, removing
events, and modifying existing events. Model is best represented by the Schedule class with all
other classes serving as Schedule dependencies.
Controller handles any functions/information regarding XML compatibility which currently consists
of the readXML and writeXML functions which are described in the Quick Start section.
View is the current view being used the visually represent the schedule planner outside the XML
files. Current visual representation is a text view that is simply a String representation of the
schedule with indentation for clarity.

Key Subcomponents:
PlannerRunner subcomponents of the model package is the Event class as the Schedule class is essentially a
list of Event objects with a name. As described in the purpose, an event holds the name of the
event, the time, the location, and the list of users attending the event. The Time class is also
significant as the time checks for any overlaps between events.
PlannerRunner subcomponents of the controller package is the ScheduleSystem interface as it contains much of
the documentation on the Controller implementation of the model. The class provides depth and
explains functionality of XML functions and its purpose.
PlannerRunner subcomponents of the view package is the ScheduleSystemView as similar to our controller
subcomponent, contains documentation of the implementation of the text view on the model. The class
provides depth and explains functionality of the text view method and its purpose.

Source Organization:
Source persists of 3 main packages: model, controller, and view. Model contains everything
pertaining the functionality of the schedule planner system. Components in the model package
consist of dependencies of the Schedule class. Controller contains everything pertaining to XML,
with reading and writing to XML files along with appropriate documentation on this functionality.
View contains the visual representation of the model, currently being a String representation along
with appropriate documentation on this functionality.

Things forgotten to include in last README edit:
Invariants:
Our invariant is found in the Time class. The time fields of Time (startTime and endTime) are invariant
with startTime and endTime being strings that are exactly 4 characters long represented by numbers where
the first 2 numbers represent the number of hours ranging from 00 to 23 and the last 2 numbers ranging
from 00 to 59. In other words the first number in the 4 number string can only hold values from 0 to 2,
the second number from 0 to 9 when the first number is 0 or 1 and from 0 to 3 when the first number is 2.
The third number ranges from 0 to 5 and the fourth number ranges from 0 to 9.

Changes since HW 5 (changes for part 2):
One implementation for the model that was absent for our previous assignment was a full planner system
that held a list of schedules and was able to add, modify, or remove an event to all applicable users
or schedules in the system. Previously our model only accounted for individual schedules that would need
to be modified for each and every applicable schedule, but now currently we are able to perform the above
described functionality inside our NUPlannerModel class. From our last assignment we also had a noted lack
of interfaces to better describe the functionality in our model so, we created and implemented the appropriate
interfaces with abundant text to describe the functionality of our code.

In order to visualize the GUI, we added classes ScheduleFrame, SchedulePanel, EventFrame, along with their
respective interfaces SchFrame, SchPanel, and EvtFrame. The interfaces SchFrame and EvtFrame are currently
empty, since their implemented class already extends either JavaFrame or JavaPanel and does not need any extra
methods. ScheduleFrame handles the button clicks and user interaction. SchedulePanel paints the components of
GUI by illustrating red blocks that represent the events of the schedule, and the grid lines. The red blocks that
appear on the main frame are activated once a user is selected. The options should be preloaded. The EventFrame
represents the way a user would be able to create or modify an event. This is shown once either the buttons
"Create event" or "Schedule event" are pressed on the main frame.

Another class that was added was PlannerRunner, which runs the main program to visualize the GUI. This initializes
preloaded users' schedules that read from prof.xml and School Schedule.xml. It also takes in a new schedule that
was initialized, called schedules1.

Screenshots and jar file is in the main folder (Outside of src)
