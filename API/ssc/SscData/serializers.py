from SscData.models import Timetable
from rest_framework import serializers

class TimetableSerializer(serializers.HyperlinkedModelSerializer):
	class Meta:
		model = Timetable
		fields = ('id', 'name', 'info', 'mode', 'day', 'hStart', 'hEnd', 'mStart', 'mEnd')
