# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render
from django.db import connection

# Create your views here.

from rest_framework import viewsets
from rest_framework import permissions
from SscData.models import Timetable
from SscData.serializers import TimetableSerializer

from django.shortcuts import render_to_response
 
def index(request):
    return render_to_response('index.html')

#API json ViewSets

class TimetableViewSet(viewsets.ModelViewSet):

	queryset = Timetable.objects.all().order_by('day', 'mode', 'hStart', 'mStart')
	serializer_class = TimetableSerializer

