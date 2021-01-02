package pl.wolny.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PreventPing extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (!(event.getAuthor().isBot())) {
            if (event.getMessage().getMentionedUsers().size() > 0) {
                for(int i=0;i<event.getMessage().getMentionedMembers().size();i++){
                    if (event.getMessage().getMentionedUsers().get(i).getId().equals("537260182225551360")) {
                        if (!event.getAuthor().isBot()) {
                            //System.out.print(event.getMessage().getMentionedUsersBag().size());
                            event.getChannel().sendMessage(event.getAuthor().getAsMention() + " Oznaczanie tego użytkownika jest nielegalne! Zostajesz wyciszony na 10 minut!").queue();
                            //event.getMessage().addReaction(Objects.requireNonNull(event.getGuild().getEmoteById("786852460052086816"))).queue();
                            Role role = event.getGuild().getRoleById("795035790542766111");
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
