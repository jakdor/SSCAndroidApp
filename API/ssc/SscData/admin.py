# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.contrib import admin

from SscData.models import Timetable
admin.site.register(Timetable)
from SscData.models import Host
admin.site.register(Host)
from SscData.models import Sponsor
admin.site.register(Sponsor)
from SscData.models import Notification
admin.site.register(Notification)

admin.site.site_header = 'SscApp backend admin panel'
