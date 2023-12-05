package RazerOfficial.Razer.gg.script.api.wrapper.impl.event.impl;

import RazerOfficial.Razer.gg.event.impl.player.KillEvent;
import RazerOfficial.Razer.gg.script.api.wrapper.impl.ScriptEntity;
import RazerOfficial.Razer.gg.script.api.wrapper.impl.event.ScriptEvent;

public class ScriptKillEvent extends ScriptEvent<KillEvent>
{
	public ScriptEntity getEntity() {
		return new ScriptEntity(this.wrapped.getEntity());
	}
	public ScriptKillEvent(KillEvent wrappedEvent)
	{
		super(wrappedEvent);
	}

	@Override
	public String getHandlerName()
	{
		return "onKill";
	}
}
