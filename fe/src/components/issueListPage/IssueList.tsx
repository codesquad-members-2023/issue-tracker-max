import { ReactComponent as CheckBoxInitial } from '@assets/icons/checkBoxInitial.svg';
import { ReactComponent as AlertCircle } from '@assets/icons/alertCircle.svg';
import { ReactComponent as UserImageSmall } from '@assets/icons/userImageSmall.svg';
import { useTheme } from '@emotion/react';
import { InformationTag } from '@components/common/InformationTag';

type Props = {
  image: string;
};

export const IssueList: React.FC<Props> = ({ image }) => {
  const theme = useTheme() as any;

  return (
    <li
      css={{
        display: 'flex',
        padding: '16px 0px',
      }}
    >
      <div
        css={{
          height: '32px',
          padding: '0px 32px',
          display: 'flex',
          alignItems: 'center',
        }}
      >
        <CheckBoxInitial
          stroke={theme.neutral.border.default}
          css={{ cursor: 'pointer' }}
        />
      </div>
      <div
        css={{
          width: '100%',
          display: 'flex',
          flexDirection: 'column',
          gap: '8px',
        }}
      >
        <div
          css={{
            width: '100%',
            display: 'flex',
            alignItems: 'center',
            gap: '8px',
          }}
        >
          <AlertCircle stroke={theme.palette.blue} />
          <span
            css={{
              font: theme.fonts.availableMedium20,
              color: theme.neutral.text.strong,
            }}
          >
            FE 이슈트래커 개발
          </span>
          <InformationTag
            size="S"
            typeVariant="filled"
            fillColor="#0025E6"
            textColor="light"
          >
            <span>documentation</span>
          </InformationTag>
        </div>
        <div
          css={{
            font: theme.fonts.displayMedium16,
            color: theme.neutral.text.weak,
          }}
        >
          #1 이 이슈가 8분 전, samsamis9님에 의해 작성되었습니다.
        </div>
      </div>
      <div css={{ padding: '0px 54px', display: 'flex', alignItems: 'center' }}>
        {image ? (
          <img
            src={image}
            alt="작성자 프로필 사진"
            css={{
              width: '20px',
              height: '20px',
              borderRadius: theme.radius.half,
            }}
          />
        ) : (
          <UserImageSmall />
        )}
      </div>
    </li>
  );
};
