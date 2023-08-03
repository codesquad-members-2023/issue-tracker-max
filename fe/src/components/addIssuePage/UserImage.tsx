import { useTheme } from '@emotion/react';
import { ReactComponent as UserImageLarge } from '@assets/icons/userImageLarge.svg';
import React, { useState } from 'react';

type Props = {
  image?: string;
};

export const UserImage: React.FC<Props> = ({ image }) => {
  const theme = useTheme() as any;
  return (
    <>
      {image && (
        <div
          css={{
            position: 'relative',
          }}
        >
          <UserImageLarge fill={theme.neutral.surface.bold} />
          <img
            alt="userImage"
            src={image}
            css={{
              width: '32px',
              height: '32px',
              position: 'absolute',
              borderRadius: theme.radius.half,
              top: 0,
              left: 0,
            }}
          />
        </div>
      )}
    </>
  );
};
