# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models

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
