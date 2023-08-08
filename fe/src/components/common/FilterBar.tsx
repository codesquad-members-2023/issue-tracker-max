import { useTheme } from '@emotion/react';
import { ReactComponent as Search } from '@assets/icons/search.svg';
import { InputContainer } from './textInput/InputContainer';
import { Input } from './textInput/Input';
import { NavigateFunction, useLocation, useNavigate } from 'react-router-dom';
import { DropDownIndicator } from './dropDown/DropDownIndicator';
import { useEffect, useState } from 'react';
import { DropDownPanel } from './dropDown/DropDownPanel';
import { DropDownList } from './dropDown/DropDownList';
import { generateEncodedQuery } from '@utils/generateEncodedQuery';

const INITIAL_FILTER_VALUE = 'status:open';

export const FilterBar: React.FC = () => {
  const theme = useTheme() as any;
  const [isPanelOpen, setIsPanelOpen] = useState(false);
  const [filterValue, setFilterValue] = useState(INITIAL_FILTER_VALUE);
  const navigate = useNavigate();
  const { search } = useLocation();

  const onChangeFilterValue = (value: string) => {
    setFilterValue(value);
  };

  const closePanel = () => {
    setIsPanelOpen(false);
  };

  const updateFilterValue = () => {
    const searchValue = decodeURIComponent(location.search).replace(
      '?query=',
      '',
    );

    setFilterValue(searchValue || INITIAL_FILTER_VALUE);
  };

  useEffect(() => {
    updateFilterValue();
  }, [search]);

  const filterItems = generateFilterItems(navigate, closePanel);

  return (
    <InputContainer height={40} radius="m" hasborderColor>
      <div
        css={{
          width: '100%',
          height: '100%',
          display: 'flex',
        }}
      >
        <div
          onClick={() => setIsPanelOpen(true)}
          css={{
            width: '128px',
            minWidth: '128px',
            cursor: 'pointer',

            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',

            borderRadius: `${theme.radius.m} 0 0 ${theme.radius.m}`,
            backgroundColor: theme.neutral.surface.default,
            borderRight: `${theme.border.default} ${theme.neutral.border.default}`,

            '&:focus-within': {
              backgroundColor: theme.neutral.surface.strong,
            },
          }}
        >
          <DropDownIndicator
            {...{
              size: 'M',
              indicator: '필터',
              isPanelOpen,
            }}
          >
            <DropDownPanel
              {...{
                position: 'left',
                panelHeader: '이슈 필터',
                onOutsideClick: closePanel,
              }}
            >
              {filterItems.map(({ id, name, onClick }) => (
                <DropDownList key={id} {...{ item: { id, name }, onClick }} />
              ))}
            </DropDownPanel>
          </DropDownIndicator>
        </div>

        <form
          css={{
            width: '100%',
            display: 'flex',
            alignItems: 'center',
            padding: '0px 25px',
            gap: '8px',
          }}
          onSubmit={(event) => {
            event.preventDefault();

            navigate('?query=' + filterValue);
          }}
        >
          <Search stroke={theme.neutral.text.default} />
          <Input
            value={filterValue}
            placeholder="Search all issues"
            onChange={onChangeFilterValue}
          />
        </form>
      </div>
    </InputContainer>
  );
};

const generateFilterItems = (
  navigate: NavigateFunction,
  closePanel: () => void,
) => [
  {
    id: 1,
    name: '열린 이슈',

    onClick: () => {
      const query = generateEncodedQuery('status', 'open');

      navigate(query);
      closePanel();
    },
  },
  {
    id: 2,
    name: '내가 작성한 이슈',
    onClick: () => {
      const query = generateEncodedQuery('status', 'open');

      navigate(query);
      closePanel();
    },
  },
  {
    id: 3,
    name: '나에게 할당된 이슈',
    onClick: () => {
      const query = generateEncodedQuery('status', 'open');

      navigate(query);
      closePanel();
    },
  },
  {
    id: 4,
    name: '내가 댓글을 남긴 이슈',
    onClick: () => {
      const query = generateEncodedQuery('status', 'open');

      navigate(query);
      closePanel();
    },
  },
  {
    id: 5,
    name: '닫힌 이슈',
    onClick: () => {
      const query = generateEncodedQuery('status', 'open');

      navigate(query);
      closePanel();
    },
  },
];
