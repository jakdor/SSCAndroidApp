from SscData.models import Timetable
from SscData.models import Host
from SscData.models import Sponsor
from rest_framework import serializers
from django.contrib.admin.models import LogEntry

class TimetableSerializer(serializers.HyperlinkedModelSerializer):
	
	index = 0
	maxSize = Timetable.objects.all().count() 

	id = serializers.SerializerMethodField()
	def get_id(self, obj):
		if TimetableSerializer.index == TimetableSerializer.maxSize:
			TimetableSerializer.index = 0
			TimetableSerializer.maxSize = Timetable.objects.all().count()
		TimetableSerializer.index += 1
		return TimetableSerializer.index


	class Meta:
		model = Timetable
		fields = ('id', 'name', 'info', 'mode', 'day', 'hStart', 'hEnd', 'mStart', 'mEnd')

class HostSerializer(serializers.HyperlinkedModelSerializer):
	
	class Meta:
		model = Host
		fields = ('id', 'name', 'info', 'mail', 'imgUrl')

class SponsorSerializer(serializers.HyperlinkedModelSerializer):
	
	class Meta:
		model = Sponsor
		fields = ('id', 'name', 'imgUrl', 'webUrl', 'mode')

class AppDataSerializer(serializers.Serializer):

	timetables = TimetableSerializer(many=True)
    	hosts = HostSerializer(many=True)
	sponsors = SponsorSerializer(many=True)

