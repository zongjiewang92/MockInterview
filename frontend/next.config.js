module.exports = {
    rewrites: async () => {
      return [
        {
          source: '/sb/:slug*',
          destination: process.env.BACKEND_URL+'/:slug*',
        },
      ]
    },
    env: {
      AUDIO_URL: process.env.AUDIO_URL,
    },
    reactStrictMode: false
  }