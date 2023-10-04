package me.fabrimat.dynmapsync.job.command.commands;

import me.fabrimat.dynmapsync.AppServer;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.CommandExecutor;
import me.fabrimat.dynmapsync.job.command.commands.exceptions.IllegalCommandArgumentsException;
import me.fabrimat.dynmapsync.job.command.exceptions.CommandException;
import me.fabrimat.dynmapsync.job.step.Step;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlCommand implements CommandExecutor {
    
    @Override
    public boolean execute(Job job, Step step, String[] args) throws CommandException {
        if (args.length == 1) {
            URL url;
            try {
                url = new URL(args[0]);
            } catch (MalformedURLException e) {
                throw new IllegalCommandArgumentsException("URL " + args[0] + " is not valid");
            }
            try {
                URLConnection urlConnection = url.openConnection();
                urlConnection.setConnectTimeout(AppServer.getInstance().getMainConfig().getUrlTimeout());
                urlConnection.connect();
            } catch (IOException e) {
                return false;
            }
        } else {
            throw new IllegalCommandArgumentsException("Wrong argument number provided");
        }
        return true;
    }
    
    @Override
    public String getCommand() {
        return "URL";
    }
}
