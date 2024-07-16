import { NextAuthConfig } from 'next-auth';
import CredentialProvider from 'next-auth/providers/credentials';
import NextAuth from 'next-auth';


const authConfig = {
  providers: [
    CredentialProvider({
      credentials: {
        userName: {
          type: 'text'
        },
        password: {
          type: 'password'
        },
        firstName: {
          type: 'text'
        },
        lastName: {
          type: 'text'
        },
        email: {
          type: 'text'
        }
      },
      async authorize(credentials, req) {
        console.log(1);
        const response = await fetch('http://localhost:8080/user/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            userName: credentials?.userName,
            passwd: credentials?.password
          })
        });

        if (response.ok) {
          const data = await response.json();
          console.log(data);
          const session = {
            name: credentials?.userName as string,
            image: data?.token as string, 
            email: data?.id as string, 
          };
          return session;
        } else {
          console.error('Error:', response.status);
        }
        return null;
      }
    })
  ],
  pages: {
    signIn: '/', //sigin page
    newUser:'/signup'
  }
} satisfies NextAuthConfig;

export default authConfig;

export const { auth, handlers, signOut, signIn } = NextAuth(authConfig);


