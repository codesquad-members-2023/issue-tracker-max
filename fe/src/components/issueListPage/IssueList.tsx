import { ReactComponent as CheckBoxInitial } from '@assets/icons/checkBoxInitial.svg';
import { ReactComponent as AlertCircle } from '@assets/icons/alertCircle.svg';
import { ReactComponent as CheckOnCircle } from '@assets/icons/checkOnCircle.svg';
import { ReactComponent as MilestoneIcon } from '@assets/icons/milestone.svg';
import { useTheme } from '@emotion/react';
import { InformationTag } from '@components/common/InformationTag';
import { formatISODateString, getFormattedTimeDifference } from '@utils/time';
import { Link } from 'react-router-dom';

type Props = {
  issue: Issue;
};

export const IssueList: React.FC<Props> = ({ issue }) => {
  const theme = useTheme() as any;
  const {
    id,
    title,
    authorLoginId,
    assignees,
    labels,
    milestone,
    createdAt,
    status,
  } = issue;

  const diff = Date.now() - new Date(createdAt).getTime();
  const date =
    diff < FIVE_DAYS_IN_MS
      ? getFormattedTimeDifference(createdAt)
      : formatISODateString(createdAt);

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
          {status === 'open' ? (
            <AlertCircle stroke={theme.palette.blue} />
          ) : (
            <CheckOnCircle stroke="#a371f7" />
          )}
          <Link
            to={`${id}`}
            css={{
              textDecoration: 'none',
              font: theme.fonts.availableMedium20,
              color: theme.neutral.text.strong,
            }}
          >
            {title}
          </Link>
          {labels.map(({ id, name, textColor, backgroundColor }) => (
            <InformationTag
              key={id}
              size="S"
              typeVariant="filled"
              fillColor={backgroundColor}
              textColor={textColor}
            >
              <span>{name}</span>
            </InformationTag>
          ))}
        </div>
        <div
          css={{
            font: theme.fonts.displayMedium16,
            color: theme.neutral.text.weak,
            display: 'flex',
            gap: '16px',
          }}
        >
          <span>{`#${id}`}</span>
          <span>{`이 이슈가 ${date}, ${authorLoginId}님에 의해 작성되었습니다.`}</span>
          {milestone && (
            <span css={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
              <MilestoneIcon fill={theme.neutral.text.weak} />
              {milestone.name}
            </span>
          )}
        </div>
      </div>
      <div
        css={{
          position: 'relative',
          display: 'flex',
          alignItems: 'center',
          marginRight: '54px',
          height: '20px',
          marginTop: 'auto',
          marginBottom: 'auto',

          ':hover div': {
            width: '35px',
          },
        }}
      >
        {assignees?.map(({ userId, image }) => (
          <div
            key={userId}
            css={{
              width: '16px',
              display: 'flex',
              justifyContent: 'right',
              transition: 'width 0.3s ease',
            }}
          >
            <img
              src={image || 'src/assets/icons/base-profile-image.jpeg'}
              alt="담당자 프로필 사진"
              css={{
                width: '32px',
                height: '32px',
                borderRadius: theme.radius.half,
              }}
            />
          </div>
        ))}
      </div>
    </li>
  );
};

const FIVE_DAYS_IN_MS = 432000000;
