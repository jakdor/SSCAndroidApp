# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render
from django.db import connection
from collections import namedtuple

# Create your views here.

from rest_framework.response import Response
from rest_framework import viewsets
from rest_framework import permissions
from SscData.models import Timetable
from SscData.serializers import TimetableSerializer
from SscData.models import Host
from SscData.serializers import HostSerializer
from SscData.models import Sponsor
from SscData.serializers import SponsorSerializer
from SscData.serializers import AppDataSerializer

from django.shortcuts import render_to_response
 
def index(request):
    return render_to_response('index.html')

#API json ViewSets

class TimetableViewSet(viewsets.ModelViewSet):

	queryset = Timetable.objects.all().order_by('day', 'mode', 'hStart', 'mStart')
	serializer_class = TimetableSerializer

class HostViewSet(viewsets.ModelViewSet):

	queryset = Host.objects.all()
	serializer_class = HostSerializer

class SponsorViewSet(viewsets.ModelViewSet):

	queryset = Sponsor.objects.all()
	serializer_class = SponsorSerializer

AppData = namedtuple('AppData', ('timetables', 'hosts', 'sponsors'))

class AppDataViewSet(viewsets.ViewSet):
   
    def list(self, request):
        appdata = AppData(
		timetables=Timetable.objects.all().order_by('day', 'mode', 'hStart', 'mStart'),
		hosts=Host.objects.all(),
		sponsors=Sponsor.objects.all(),
        )
        serializer = AppDataSerializer(appdata)
        return Response(serializer.data)



