from SscData.models import Timetable
from rest_framework import serializers

class TimetableSerializer(serializers.HyperlinkedModelSerializer):
	
	index = 0

	id = serializers.SerializerMethodField()
	def get_id(self, obj):
		TimetableSerializer.index += 1
		return TimetableSerializer.index

	class Meta:
		model = Timetable
		fields = ('id', 'name', 'info', 'mode', 'day', 'hStart', 'hEnd', 'mStart', 'mEnd')
