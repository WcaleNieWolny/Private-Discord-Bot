package pl.wolny.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MessageReciveEvent extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(!(event.getAuthor().isBot())){
            if (event.getMessage().getContentRaw().contains("XD") || (event.getMessage().getContentRaw().contains("xD"))) {
                if (!event.getMessage().getAuthor().isBot()) {
                    Message msg = event.getMessage();
                    Guild guild = event.getGuild();
                    msg.addReaction(Objects.requireNonNull(guild.getEmoteById("781777890911584276"))).queue();
                    //event.getChannel().sendMessage("```" + msg.getEmotes().get(0).getId() + "```").queue();
                }
            } else if (event.getMessage().getContentRaw().toLowerCase().contains("JungleSurvival".toLowerCase())) {
                if (!event.getMessage().getAuthor().isBot()) {
                    Message msg = event.getMessage();
                    Guild guild = event.getGuild();
                    msg.addReaction(Objects.requireNonNull(guild.getEmoteById("780448896257490974"))).queue();
                }
            } else if (event.getMessage().getContentRaw().toLowerCase().contains("ip")) {
                List<String> msg = Arrays.asList(event.getMessage().getContentRaw().split(" "));
                for(int i=0;i<event.getMessage().getContentRaw().split(" ").length;i++){
                    if(msg.get(i).equalsIgnoreCase("ip")){
                        event.getChannel().sendMessage("IP najlepszego serwera minecraft to **junglesurvival.pl** \uD83D\uDC9A").queue();
                        return;
                    }
                }
            } else if (event.getMessage().getContentRaw().toLowerCase().contains("wersja")) {
                event.getChannel().sendMessage("**Wersja serwera 1.16.4**").queue();
            } else if (event.getMessage().getMentionedUsers().size() > 0) {
                for(int i=0;i<event.getMessage().getMentionedMembers().size();i++){
                    if(Objects.requireNonNull(event.getGuild().getMemberById(event.getMessage().getMentionedUsers().get(i).getId())).getRoles().size()>0){
                        if(Objects.requireNonNull(event.getGuild().getMemberById(event.getMessage().getMentionedUsers().get(i).getId())).getRoles().contains(event.getGuild().getRoleById("775326118680985621"))){
                            System.out.print("true-dawdawd");
                        }
                    }
                    if (event.getMessage().getMentionedUsers().get(i).getId().equals("537260182225551360")) {
                        if (!event.getAuthor().isBot()) {
                            System.out.print(event.getMessage().getMentionedUsersBag().size());
                            event.getChannel().sendMessage(event.getAuthor().getAsMention() + " Oznaczanie tego użytkownika jest nielegalne! Zostajesz wyciszony na 10 minut!").queue();
                            event.getMessage().addReaction(Objects.requireNonNull(event.getGuild().getEmoteById("786852460052086816"))).queue();
                            Role role = event.getGuild().getRoleById("762568841087156244");
                            event.getGuild().addRoleToMember(event.getAuthor().getId(), role).queue();
                            event.getAuthor().openPrivateChannel().queue((channel) ->
                            {
                                EmbedBuilder unmute = new EmbedBuilder();
                                unmute.setTitle("Koniec kary");
                                channel.sendMessage(unmute.build()).queueAfter(10, TimeUnit.MINUTES);
                            });
                            event.getGuild().removeRoleFromMember(event.getAuthor().getId(), role).queueAfter(10, TimeUnit.MINUTES);
                        }
                    }
                }
            }
        }
    }
}
