import logging

from django.conf import settings
from rest_framework import response, status
from rest_framework.views import APIView
from requests_oauthlib import OAuth2Session
from oauthlib.oauth2 import BackendApplicationClient

logger = logging.getLogger(__name__)


class ExchangeAuthorizationCode(APIView):

    def get(self, request):
        """Endpoint for OAuth 2.0 Authorization Code flow

        Receive redirected response from Authorization Server (Fitbit)
        with Authorization Code. Then exchange Authorization Code for
        Access Token.
        To complete sing-up/sing-in flow make request to convert-token endpoint
        and returns Django token
        """
        try:
            authorization_code = request.query_params['code']
        except KeyError:
            error_msg = 'Missing required query parameters'
            logger.error(error_msg)
            return response.Response(status=status.HTTP_422_UNPROCESSABLE_ENTITY,
                                     data={'message': error_msg})
        fitbit_access_token_url = settings.SOCIAL_AUTH_FITBIT_ACCESS_TOKEN_URL
        fitbit_client_id = settings.SOCIAL_AUTH_FITBIT_KEY
        fitbit_client_secret = settings.SOCIAL_AUTH_FITBIT_SECRET
        fitbit_app_client = BackendApplicationClient(client_id=fitbit_client_id)
        fitbit_oauth_session = OAuth2Session(client=fitbit_app_client)
        access_token = fitbit_oauth_session.fetch_token(token_url=fitbit_access_token_url,
                                                 client_id=fitbit_client_id,
                                                 client_secret=fitbit_client_secret,
                                                 code=authorization_code)
        # TODO sign up with access_token and django-rest-framework-social-oauth2
        return response.Response(status=status.HTTP_200_OK)
