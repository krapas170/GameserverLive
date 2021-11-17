package com.minecraftstatus.managers;

import javax.swing.Timer;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.ShardManager;

public class StatusManager {

    private Timer timer;
    private ShardManager bot;
    private String[] activitys; 
    private int time;

    private final String WACHING_KEY = "-w ";
    private final String LISTENING_KEY = "-l ";
    private final String STREAMING_KEY = "-s ";

    public StatusManager(ShardManager bot, int time, String... activitys) {
        this.bot = bot;
        this.activitys = activitys;
        this.time = time;
        startTimer();
    }

    public void startTimer() {
        timer = new Timer(time * 1000, l -> onStatusChange());
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    private void onStatusChange() {
        String activity = activitys[rand(activitys.length)];
        setStatus(prep(activity, bot));
    }

    private void setStatus(String activity) {
        if (activity.startsWith(WACHING_KEY))
            setStatus(Activity.watching(activity.replace(WACHING_KEY, "")));
        else if (activity.startsWith(LISTENING_KEY))
            setStatus(Activity.listening(activity.replace(LISTENING_KEY, "")));
        else if (activity.startsWith(STREAMING_KEY))
            setStatus(Activity.streaming(activity.replace(STREAMING_KEY, ""), "Wat n das?"));
        else
            setStatus(Activity.watching(activity));
    }

    private void setStatus(Activity activity) {
        bot.getShards().forEach(jda -> jda.getPresence().setActivity(activity));
    }

    private String prep(String string, ShardManager bot) {
        int membersCount = bot.getUsers().size();
        int guildsCount = bot.getGuilds().size();
        
        return string.replaceAll("%server", "" + guildsCount)
            .replaceAll("%members", "" + membersCount);

    }

    private int rand(int range) {
        return (int)(Math.random() * range);
    }
    
}
