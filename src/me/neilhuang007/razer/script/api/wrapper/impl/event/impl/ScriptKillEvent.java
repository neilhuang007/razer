package me.neilhuang007.razer.script.api.wrapper.impl.event.impl;

import me.neilhuang007.razer.newevent.impl.other.KillEvent;
import me.neilhuang007.razer.script.api.wrapper.impl.ScriptEntity;
import me.neilhuang007.razer.script.api.wrapper.impl.event.ScriptEvent;

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
