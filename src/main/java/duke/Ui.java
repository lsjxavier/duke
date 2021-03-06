/**
 * The UI class handles all string messages to be shown by Duke.
 */

package duke;

public class Ui {

    static final String COMMAND_NOT_FOUND = "Oops! Command not found!";
    static final String INVALID_FIELD = "Oops! Invalid field!";
    static final String TASK_NEEDS_NAME = "Oops! This task needs a name!";
    static final String TASK_NEEDS_DATE_TIME = "Oops! This task needs a date/time!";
    static final String WRONG_DATE_TIME_FORMAT = "Oops! Invalid date/time format or invalid date!";
    static final String NO_TASK_FOUND = "Oops! Task not found in the list!";
    static final String NO_TASK_IN_LIST = "Yay! There are no tasks in your list!";
    static final String DISPLAY_TASK_LIST = "Here are all the tasks in your list:";
    static final String DISPLAY_MATCHING_TASK_LIST = "Here are your tasks that matched your query:";
    static final String NO_MATCHING_TASK_IN_LIST = "Oops! There are no tasks that matches your query!";
    static final String CHANGES_SAVED = "Changes saved!";
    static final String MARKED_AS_DONE = "Marked this task as done:\n    ";
    static final String TASK_ALREADY_DONE = "Oops! This task was already marked as done!";
    static final String ADDED_NEW_TASK = "New task has been added:\n    ";
    static final String DELETED_TASK = "Removed this task:\n    ";
    static final String UPDATED_TASK = "Updated this task to:\n    ";
    static final String NO_FIELD_TO_UPDATE = "No field to update!";
    static final String CANNOT_SET_DATE_TIME_TO_TODO = "Cannot set date or time to todo!";
    static final String CANNOT_SORT_TODO_BY_DATE_TIME = "Cannot sort todo by date/time!";
    static final String NEED_TO_SPECIFY_PERIOD = "Need to specify period!";
    static final String NEED_TO_SPECIFY_DATE = "Need to specify date!";
    static final String WELCOME_MESSAGE = "Welcome to Duke!\n";
    static final String NOT_ENOUGH_PARAMETERS = "Not enough information to process your request!";
    static final String SORTED_BY_NAME = "Sorted your tasks by name.";
    static final String SORTED_BY_DATE_TIME = "Sorted your tasks by date.";


    /**
     * Reads a String line, parses it and sends to the main logic for processing.
     * @param input The full input by the user.
     * @param taskLists An array of TaskLists
     * @return The output message from passing the input into the main logic
     */
    public static String readNextCommand(String input, TaskList[] taskLists) {
        String[] command = Parser.parseInput(input.strip());
        return Parser.processCommand(command, taskLists);
    }

    /**
     * Presents the number of tasks in any TaskList
     * @param listSize The size of the list.
     * @return A message containing the number of tasks in the list.
     */
    public static String taskCountMessage(int listSize) {
        return "\nNow you have "+ listSize + (listSize == 1 ? " task" : " tasks") + " in the list.";
    }


    /**
     * The main message when the user types in an 'upcoming' command.
     * @param dayRangeUntil The number of days in the range.
     * @param upcomingDeadlines The list of upcoming deadlines occurring within the range.
     * @param upcomingEvents The list of upcoming events occurring within the range.
     * @return a string representation of an overview of deadlines and events.
     */
    public static String displayUpcomingRange(int dayRangeUntil, TaskList upcomingDeadlines, TaskList upcomingEvents) {
        String deadlines = messageUpcomingDeadlines(upcomingDeadlines) + "for the next " + dayRangeUntil + " day(s).\n";;
        if (!upcomingDeadlines.isEmpty()) {
            deadlines = deadlines + upcomingDeadlines + "\n";
        }

        String events = messageUpcomingEvents(upcomingEvents) + "for the next " + dayRangeUntil + " day(s).\n";;
        if (!upcomingEvents.isEmpty()) {
            events = events + upcomingEvents + "\n";
        }
        return deadlines + events;
    }

    /**
     *
     * @param targetDate The target date the user requests.
     * @param upcomingDeadlines The list of deadlines happening on the targetDate.
     * @param upcomingEvents The list of events happening on the targetDate.
     * @return a string representation of an overview of deadlines and events.
     */
    public static String displayViewDay(String targetDate, TaskList upcomingDeadlines, TaskList upcomingEvents) {
        String deadlines = messageUpcomingDeadlines(upcomingDeadlines) + "on " + targetDate + ".\n";
        if (!upcomingDeadlines.isEmpty()) {
            deadlines = deadlines + upcomingDeadlines + "\n";
        }

        String events = messageUpcomingEvents(upcomingEvents) + "on " + targetDate + ".\n";
        if (!upcomingEvents.isEmpty()) {
            events = events + upcomingEvents + "\n";
        }
        return deadlines + events;
    }

    /**
     * Returns the starting message of both 'upcoming' and 'view' commands for the deadline list. Also sorts the list
     * by date/time.
     * @param upcomingDeadlines the list of upcoming deadlines.
     * @return a string message.
     */
    public static String messageUpcomingDeadlines(TaskList upcomingDeadlines) {
        if (upcomingDeadlines.isEmpty()) {
            return "You have no upcoming deadlines ";
        }
        upcomingDeadlines.sortDateTime();
        return "Your upcoming deadlines ";
    }

    /**
     * Returns the starting message of both 'upcoming' and 'view' commands for the event list. Also sorts the list
     * by date/time.
     * @param upcomingEvents the list of upcoming events.
     * @return a string message.
     */
    public static String messageUpcomingEvents(TaskList upcomingEvents) {
        if (upcomingEvents.isEmpty()) {
            return "You have no upcoming events ";
        }
        upcomingEvents.sortDateTime();
        return "Your upcoming events ";
    }

}