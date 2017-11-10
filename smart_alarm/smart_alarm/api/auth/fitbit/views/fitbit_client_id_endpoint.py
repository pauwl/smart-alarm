import logging

from django.conf import settings
from rest_framework import response, status
from rest_framework.views import APIView

logger = logging.getLogger(__name__)


class FitbitClientId(APIView):

    def get(self, request):
        fitbit_client_id = getattr(settings, 'SOCIAL_AUTH_FITBIT_KEY')

        return response.Response(status=status.HTTP_200_OK,
                                 data={'fitbit_client_id': fitbit_client_id})
