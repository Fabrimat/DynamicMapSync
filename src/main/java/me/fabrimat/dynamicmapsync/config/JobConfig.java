package me.fabrimat.dynamicmapsync.config;

import me.fabrimat.dynamicmapsync.DynamicMapSync;
import me.fabrimat.dynamicmapsync.job.Job;
import me.fabrimat.dynamicmapsync.job.JobScheduleInfo;
import me.fabrimat.dynamicmapsync.job.command.Command;
import me.fabrimat.dynamicmapsync.job.step.Step;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public final class JobConfig extends ConfigManager {

    private final Set<Job> jobs;

    public JobConfig() {
        this.jobs = new HashSet<>();
    }

    public void loadConfiguration() {
        try {
            loadConfig("jobs.yml", ConfigurationProvider.getProvider(YamlConfiguration.class));
        } catch (IOException e) {
            DynamicMapSync.getInstance().getLogger().log(Level.SEVERE, "Error loading jobs.yml", e);
        }
    }

    @Override
    protected void loadConfig(String fileName, ConfigurationProvider configurationProvider) throws IOException {
        super.loadConfig(fileName, configurationProvider);

        Configuration jobSection = getConfiguration().getSection("jobs");

        if (jobSection != null) {
            for (String jobName : jobSection.getKeys()) {
                Configuration jobConfig = jobSection.getSection(jobName);

                JobScheduleInfo scheduleInfo = null;
                if (jobConfig.contains("schedule")) {
                    Object delayObj = jobConfig.get("schedule.delay", null);
                    Long delay = (delayObj instanceof Number) ? ((Number) delayObj).longValue() : null;
                    Object periodObj = jobConfig.get("schedule.period", null);
                    Long period = (periodObj instanceof Number) ? ((Number) periodObj).longValue() : null;

                    scheduleInfo = new JobScheduleInfo(delay, period, TimeUnit.SECONDS);
                }
                Job job = new Job(jobName, scheduleInfo);

                if (jobConfig.contains("steps")) {
                    Configuration stepConfig = jobConfig.getSection("steps");
                    List<Integer> orderedKeyList = new ArrayList<>();
                    for (String key : stepConfig.getKeys()) {
                        try {
                            Integer value = Integer.parseInt(key);
                            orderedKeyList.add(value);
                        } catch (NumberFormatException ignored) {
                        }
                    }
                    Collections.sort(orderedKeyList);
                    for (Integer stepId : orderedKeyList) {
                        Configuration step = stepConfig.getSection(stepId.toString());
                        boolean conditional = step.getBoolean("conditional", false);
                        Command command = new Command(step.getString("command", null));
                        Command fallbackCommand = new Command(step.getString("command-fallback", null));
                        job.addStep(new Step(job, command, fallbackCommand, conditional));
                    }
                }

                getJobs().add(job);
            }
        }
    }

    private Set<Job> getJobs() {
        return jobs;
    }

    public Set<Job> getLoadedJobs() {
        return new HashSet<>(getJobs());
    }
}
