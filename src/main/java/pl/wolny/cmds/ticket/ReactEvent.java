package pl.wolny.cmds.ticket;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.EnumSet;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ReactEvent extends ListenerAdapter {
    public void onMessageReactionAdd(MessageReactionAddEvent event){
        event.getChannel().retrieveMessageById(event.getMessageId()).queue(message -> {
            if(message.getEmbeds().size() > 0){
                if(Objects.requireNonNull(message.getEmbeds().get(0).getTitle()).contains("Kliknij") && (Objects.requireNonNull(message.getEmbeds().get(0).getTitle()).contains("utworzy") && (Objects.requireNonNull(message.getEmbeds().get(0).getTitle()).contains("ticket")))){
                    if(!(Objects.requireNonNull(event.getMember()).getUser().isBot())){
                        message.removeReaction("\uD83D\uDCE7", Objects.requireNonNull(event.getUser())).queue();
                        createTicket(event.getMember(), event.getGuild().getCategoryById("794228427062247434"));
                    }
                }else if(Objects.requireNonNull(message.getEmbeds().get(0).getTitle()).contains("Ticket-")){
                    if(!(Objects.requireNonNull(event.getMember()).getUser().isBot())){
                        if(event.getReactionEmote().getEmoji().equals("⛔")){
                            EmbedBuilder embedBuildel = new EmbedBuilder();
                            embedBuildel.setColor(Color.red);
                            embedBuildel.setTitle("Kanał zostanie usunięty za 10 sekund!");
                            event.getChannel().sendMessage(embedBuildel.build()).queue();
                            Objects.requireNonNull(event.getGuild().getTextChannelById(event.getChannel().getId())).delete().reason("Ticket Usunięty").queueAfter(10, TimeUnit.SECONDS);
                        }
                    }
                }
            }
        });
    }
    public static void createTicket(Member member, Category cat) {
        Guild guild = member.getGuild();
        if(guild.getTextChannelsByName("Ticket-" + member.getId(), true).size() < 1){
            guild.createTextChannel("Ticket-" + member.getId(), cat)
                    .addPermissionOverride(member, EnumSet.of(Permission.VIEW_CHANNEL), null)
                    .addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL))
                    .addPermissionOverride(Objects.requireNonNull(guild.getRoleById("775326441348923423")), EnumSet.of(Permission.VIEW_CHANNEL), null)
                    .queue(); // this actually sends the request to discord.
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.green);
            eb.setTitle("Ticket-" + member.getId());
            eb.addField("Wiadomośc", "Kliknij ⛔ aby zamknąć ticket", false);
            try
            {
                Thread.sleep(500);
            }
            catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            guild.getTextChannelsByName("Ticket-" + member.getId(), true).get(0).sendMessage(eb.build()).queue(message -> {
                message.addReaction("⛔").queue();
            });
            guild.getTextChannelsByName("Ticket-" + member.getId(), true).get(0).sendMessage(member.getAsMention()).queue();
        }
    }
}
