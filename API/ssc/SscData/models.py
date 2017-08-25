# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models
from django.db.models.signals import post_save
from SscData.pushNotif import pushNotification

class Timetable(models.Model):	
	def __unicode__(self):
		return str(self.id) + '. ' + self.name + ' | day: ' + str(self.day) + ' | mode: ' + str(self.mode) + ' | time: ' + str(self.hStart) + ':' + str(self.mStart) + '-' + str(self.hEnd) + ':' + str(self.mEnd)

	name = models.CharField(max_length=127)
	info = models.CharField(max_length=1023, blank=True)
	mode = models.IntegerField()
	day = models.IntegerField()
	hStart = models.IntegerField()
	mStart = models.IntegerField()
	hEnd = models.IntegerField()
	mEnd = models.IntegerField()

	class Meta:
        	ordering = ["id"]

class Host(models.Model):
	def __unicode__(self):
		return str(self.id) + '. ' + self.name + ' | ' + self.info

	name = models.CharField(max_length=127)
	info = models.CharField(max_length=255)
	mail = models.CharField(max_length=255)
	imgUrl = models.CharField(max_length=255)

	class Meta:
        	ordering = ["id"]

class Sponsor(models.Model):
	def __unicode__(self):
		return str(self.id) + '. ' + self.name

	name = models.CharField(max_length=255, blank=True)
	imgUrl = models.CharField(max_length=255)
	webUrl = models.CharField(max_length=255, blank=True)
	mode = models.IntegerField()

	class Meta:
        	ordering = ["id"]

class Notification(models.Model):
	def __unicode__(self):
		return 'Title: ' + self.title + ' Message: ' + self.message

	title = models.CharField(max_length=63)
	message = models.CharField(max_length=255)

	class Meta:
        	ordering = ["id"]

def post_save_actions(sender, instance, created, **kwargs):
	print('Sending notification id: ' + str(instance.id))
	pushNotification(instance.title, instance.message)

post_save.connect(post_save_actions, sender=Notification)
