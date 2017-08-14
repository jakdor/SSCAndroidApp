# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models

class Timetable(models.Model):
	name = models.CharField(max_length=127)
	info = models.CharField(max_length=1023, blank=True)
	mode = models.IntegerField()
	day = models.IntegerField()
	hStart = models.IntegerField()
	mStart = models.IntegerField()
	hEnd = models.IntegerField()
	mEnd = models.IntegerField()
